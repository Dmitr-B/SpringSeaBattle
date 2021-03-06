package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.dto.Shot;
import com.seabattlespring.springseabattle.game.state.GameContext;
import com.seabattlespring.springseabattle.game.validator.ship.*;
import com.seabattlespring.springseabattle.game.validator.ship.exception.*;
import com.seabattlespring.springseabattle.game.validator.shot.ShotValidator;
import com.seabattlespring.springseabattle.game.validator.shot.exception.ShotException;
import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.RedisGameRepository;
import com.seabattlespring.springseabattle.repository.StatRepository;
import com.seabattlespring.springseabattle.repository.UserRepository;
import com.seabattlespring.springseabattle.repository.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final StatRepository statRepository;
    private final ShotValidator shotValidator;
    private final RedisGameRepository redisGameRepository;
    private final ShipValidator shipValidator = new NumberOfCoordinatesValidator(new OneStraightLineValidator(
            new NearbyCoordinatesValidator(new NumberOfValidShipTypeValidator(
                    new CellEmptyValidator(null)))));

    GameContext gameContext = new GameContext();

    public String createGame(String userName) {
        Game game = new Game();
        String userId = getUserIdByUserName(userName);

        game.setUser1(userId);

        Game updatedGame = gameRepository.save(game);
        redisGameRepository.addAvailableGame("available_games", updatedGame.getId(), 60000);
        log.info("Game:" + updatedGame.getId() + " created by user: " + userId);
        return updatedGame.getId();
    }

    public Game getGameById(String id) {
        return gameRepository.findGameById(id);
    }

    public void joinGameById(String id, String userName) {
        Game game = getGameById(id);
        String userId = getUserIdByUserName(userName);

        if (!game.getUser1().equals(userId) && (game.getUser1()!= null && game.getUser2() == null)) {
            game.setUser2(userId);
        } else throw new IllegalArgumentException("This game is already taken");

        gameRepository.save(game);
    }

    public void joinToRandomGame(String userName) {
        String userId = getUserIdByUserName(userName);
        String availableId = getAvailableId();

        if (availableId != null) {
            Game game = gameRepository.findGameById(availableId);
            game.setUser2(userId);
            gameRepository.save(game);
            log.info("User: " + userId + " is joining to random game by id: " + game.getId());
        } else  throw new IllegalArgumentException("There are no free games now");

    }

    public String getAvailableId() {
        String availableGame;
        String[] data;
        Date date = new Date();
        do {
            availableGame = redisGameRepository.getAvailableGame("available_games");

            if (availableGame == null)
                return null;

            data = availableGame.split("::");

        } while (Long.parseLong(data[1]) < date.getTime());

        return data[0];
    }

    public void addShip(String id, String userName, FightField.Owner owner, Ship ship) throws NumberOfCoordinatesException, OneStraightLineException,
            NearbyCoordinatesException, NumberOfValidShipException, CellEmptyException, PlayerException {
        Game game = gameRepository.findGameById(id);
        String userId = getUserIdByUserName(userName);
        getCurrentState(game);
        //todo ???????????????????? ???????? ??????
        if (State.ARRANGEMENT.equals(game.getState()) && isValidUserId(game, userId)) {
            FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                    .filter(field -> owner.equals(field.getOwner()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("field not found"));

            if (shipValidator.valid(fightField, ship)) {
                ShipDto shipDto = new ShipDto();
                shipDto.setShipType(ship.getShipType());
                shipDto.setCells(ship.getCells());

                for (int i = 0; i < ship.getCells().size(); i++) {

                    fightField.getCells().get(ship.getCells().get(i).getCoordinates().getX()).get(ship.getCells().get(i).getCoordinates().getY())
                            .setCellState(ship.getCells().get(i).getCellState());

                }

                fightField.getShips().add(shipDto);
                setCellArea(fightField, shipDto);

                gameContext.doChangeGameState(game);

                //todo ???????? ?????? ??????????????????????, ???? ?????????????? ????????????????????

                gameRepository.save(game);
            }
        }

    }

    public CellState shot(String id, String userName, FightField.Owner owner, Shot shot) throws ShotException, PlayerException {
        Game game = gameRepository.findGameById(id);
        String userId = getUserIdByUserName(userName);
        gameContext.getCurrentState(game);
        //todo ???????????????????? ???????? ??????

        CellState newCellState = null;

        FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                .filter(field -> !owner.equals(field.getOwner()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("field not found"));

        if (shotValidator.valid(game, fightField) && isValidUserId(game, userId)) {

            Cell cell = fightField.getCells().get(shot.getCoordinates().getX()).get(shot.getCoordinates().getY());

            if (CellState.SHIP.equals(cell.getCellState())) {
                newCellState = resultOfTheShot(fightField, shot.getCoordinates());
            } else {
                //todo change turn player
                newCellState = CellState.PAST;
                gameContext.changeTurnPlayer();
            }
            cell.setCellState(newCellState);

            //todo ???? ?????? ??????????????????

            gameContext.doChangeGameState(game);

            if (game.getWinner() != null) {
                defineWinner(game);
            }

            gameRepository.save(game);
        }

        return newCellState;
    }

    private CellState resultOfTheShot(FightField fightField, Coordinates coordinates) {

        ShipDto shipDto = fightField.getShips().stream()
                .filter(ship -> ship.getCells().stream().anyMatch(cell -> cell.getCoordinates().equals(coordinates)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ship not found"));

        long count = shipDto.getCells().stream()
                .filter(ship -> ship.getCellState().equals(CellState.SHIP))
                .count();

        if (count == 1) {
            setSunkState(fightField, shipDto);

            return CellState.SUNK;
        }

        setKnockedSTate(fightField, shipDto, coordinates);

        return CellState.KNOCKED;
    }

    private void setSunkState(FightField fightField, ShipDto shipDto) {
        for (int i = 0; i < shipDto.getCells().size(); i++) {
            int x = shipDto.getCells().get(i).getCoordinates().getX();
            int y = shipDto.getCells().get(i).getCoordinates().getY();

            shipDto.getCells().get(i).setCellState(CellState.SUNK);
            fightField.getCells().get(x).get(y).setCellState(CellState.SUNK);
        }
    }

    private void setKnockedSTate(FightField fightField, ShipDto shipDto, Coordinates coordinates) {
        fightField.getCells().get(coordinates.getX()).get(coordinates.getY()).setCellState(CellState.KNOCKED);

        shipDto.getCells().stream()
                .filter(knockedCell -> knockedCell.getCoordinates().equals(coordinates))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("cell not found"))
                .setCellState(CellState.KNOCKED);
    }

    private void setCellArea(FightField fightField, ShipDto shipDto) {
        int fromX = Math.max((shipDto.getCells().get(0).getCoordinates().getX() - 1), 0);
        int fromY = Math.max((shipDto.getCells().get(0).getCoordinates().getY() - 1), 0);
        int toX = Math.min((shipDto.getCells().get(shipDto.getCells().size()-1).getCoordinates().getX() + 1), 9);
        int toY = Math.min((shipDto.getCells().get(shipDto.getCells().size()-1).getCoordinates().getY() + 1), 9);

        for (int x = fromX; x <= toX; x++) {
            for (int y = fromY; y <= toY; y++) {
                if (CellState.EMPTY.equals(fightField.getCells().get(x).get(y).getCellState())) {
                    fightField.getCells().get(x).get(y).setCellState(CellState.AREA);
                }
            }
        }
    }

    private void getCurrentState(Game game) {
        gameContext.getCurrentState(game);
    }

    private boolean isValidUserId(Game game, String userId) throws PlayerException {
        if (game.getUser1().equals(userId) || game.getUser2().equals(userId)) {
            return true;
        } else throw new PlayerException("User not found in a game");
    }

    private String getUserIdByUserName(String userName) {
        User user = userRepository.getByUserName(userName);
        return user.getId();
    }

    private void defineWinner(Game game) {
        User user1 = userRepository.getById(game.getUser1());
        User user2 = userRepository.getById(game.getUser2());

        switch (game.getWinner()) {
            case "PLAYER1":
                changeStat(user1.getUserName(), user2.getUserName());
            break;
            case "PLAYER2":
                changeStat(user2.getUserName(), user1.getUserName());
            break;
        }
    }

    private void changeStat(String winner, String looser) {

        if (!statRepository.saveIfNotPresent("win", winner)) {
            statRepository.incrementScore("win", winner);
            statRepository.incrementScore("game", winner);
        } else {
            statRepository.save("game", winner, 1);
        }

        if (statRepository.getScoreByValue("lose", winner) == null) {
            statRepository.save("lose", winner, 0);
        }

        if (!statRepository.saveIfNotPresent("lose", looser)) {
            statRepository.incrementScore("lose", looser);
            statRepository.incrementScore("game", looser);
        } else {
            statRepository.save("game", looser, 1);
        }

        if (statRepository.getScoreByValue("win", looser) == null) {
            statRepository.save("win", looser, 0);
        }
    }
}
