package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.dto.Shot;
import com.seabattlespring.springseabattle.game.validator.ship.exception.*;
import com.seabattlespring.springseabattle.game.state.*;
import com.seabattlespring.springseabattle.game.validator.ship.*;
import com.seabattlespring.springseabattle.game.validator.shot.ShotValidator;
import com.seabattlespring.springseabattle.game.validator.shot.exception.ShotException;
import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.UserRepository;
import com.seabattlespring.springseabattle.repository.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    //@Autowired
    private final ShotValidator shotValidator;
    //@Qualifier("numberOfCoordinatesValidator")
    //@Autowired
    private final ShipValidator shipValidator = new NumberOfCoordinatesValidator(new OneStraightLineValidator(
            new NearbyCoordinatesValidator(new NumberOfValidShipTypeValidator(
                    new CellEmptyValidator(null)))));
    //private final GameContext gameContext;
    GameContext gameContext = new GameContext();

    public String createGame(String userName) {
        Game game = new Game();
        String userId = getUserIdByUserName(userName);

        game.setUser1(userId);

        gameRepository.save(game);
        log.info("Game created by user: " + userId);
        return game.getId();
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
        List<Game> games = gameRepository.findAllByUser1IsNotNull().stream()
                .filter(user -> user.getUser2() == null)
                .collect(Collectors.toList());

        if (games.size() > 0) {
            //Random random = new Random();

            //Game game = games.get(random.nextInt(games.size()));
            Game game = games.stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("There are no free games now"));
            game.setUser2(userId);
            gameRepository.save(game);
            log.info("User: " + userId + " is joining to random game by id: " + game.getId());
        }

        //log.info(games);
    }

    public void addShip(String id, String userName, FightField.Owner owner, Ship ship) throws NumberOfCoordinatesException, OneStraightLineException,
            NearbyCoordinatesException, NumberOfValidShipException, CellEmptyException {
        Game game = gameRepository.findGameById(id);
        String userId = getUserIdByUserName(userName);
        getCurrentState(game);
        //todo перевірити стан гри
        if (State.ARRANGEMENT.equals(game.getState()) && isValidUserId(game, userId)) {
            FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                    .filter(field -> owner.equals(field.getOwner()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("field not found"));

            //ShipValidator shipValidator = createValidator();

            log.info("valid " + shipValidator.valid(fightField, ship));

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

                //log.info("stateFight " + isFightStateGame(game));

            /*if (isFightStateGame(game)) {
                game.setState(State.FIGHT);
            }*/
                gameContext.doChangeGameState(game);

                //todo перевіряти що гра готова і змінити стан гри

                gameRepository.save(game);
            }
        }

    }

    public CellState shot(String id, String userName, FightField.Owner owner, Shot shot) throws ShotException {
        Game game = gameRepository.findGameById(id);
        String userId = getUserIdByUserName(userName);
        gameContext.getCurrentState(game);
        //todo перевірити стан гри

        CellState newCellState = null;

            FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                    .filter(field -> !owner.equals(field.getOwner()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("field not found"));

        System.out.println("eblan " + fightField.getOwner());
        System.out.println("pidar " + gameContext.getGameState());

        if (/*State.FIGHT.equals(game.getState())*//*(gameContext.getGameState() instanceof Player1TurnState && State.PLAYER1TURN.equals(game.getState()) ||
                (gameContext.getGameState() instanceof Player2TurnState && State.PLAYER2TURN.equals(game.getState())))*/
                shotValidator.valid(game, fightField) && isValidUserId(game, userId)/*isValidShot(game, fightField)*/) {
            //log.info("shot " + isValidShot(game, fightField));
            log.info("state " + gameContext.getGameState());

            Cell cell = fightField.getCells().get(shot.getCoordinates().getX()).get(shot.getCoordinates().getY());

            //
            if (CellState.SHIP.equals(cell.getCellState())) {
                newCellState = resultOfTheShot(fightField, shot.getCoordinates());
            } else {
                //todo change turn player
                newCellState = CellState.PAST;
//                if (gameContext.getGameState() instanceof Player1TurnState) {
//                    gameContext.changeGameState(new Player2TurnState(gameContext));
//                } else {
//                    gameContext.changeGameState(new Player1TurnState(gameContext));
//                }
                gameContext.changeTurnPlayer();
            }
            cell.setCellState(newCellState);

            //todo чи гра завершена

            //log.info("gameOver " + isGameOver(game));

//            if (isGameOver(game)) {
//                game.setState(State.OVER);
//            }
            gameContext.doChangeGameState(game);

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

//    private boolean isFightStateGame(Game game) {
//        FightField fightField1 = game.getFightField1();
//        FightField fightField2 = game.getFightField2();
//        log.info("size1 " + fightField1.getShips().size());
//        log.info("size2 " + fightField2.getShips().size());
//
//        return fightField1.getShips().size() == 1 && fightField2.getShips().size() == 1;
//    }

//    private boolean isGameOver(Game game) {
//
//        boolean overPlayer1 = game.getFightField1().getShips().stream()
//                .filter(shipDto1 -> shipDto1.getCells().stream().anyMatch(cell -> cell.getCellState().equals(CellState.SHIP)))
//                .findAny()
//                .isEmpty();
//
//        boolean overPlayer2 = game.getFightField2().getShips().stream()
//                .filter(shipDto1 -> shipDto1.getCells().stream().anyMatch(cell -> cell.getCellState().equals(CellState.SHIP)))
//                .findAny()
//                .isEmpty();
//
//        return overPlayer1 || overPlayer2;
//    }

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

//    private ShipValidator createValidator() {
//        return  new NumberOfCoordinatesValidator(new OneStraightLineValidator(
//                new NearbyCoordinatesValidator(new NumberOfValidShipTypeValidator(
//                        new CellEmptyValidator(null)))));
//    }

//    private boolean isValidShot(Game game, FightField fightField) {
//
//        switch (game.getState()) {
//            case PLAYER1TURN:
//                if (FightField.Owner.PLAYER2.equals(fightField.getOwner())) {
//                    log.info("shooot1 " + fightField.getOwner());
//                    return true;
//                }
//                break;
//            case PLAYER2TURN:
//                if (FightField.Owner.PLAYER1.equals(fightField.getOwner())) {
//                    log.info("shooot2 " + fightField.getOwner());
//                    return true;
//                }
//                break;
//        }
//        return false;
//    }

    private void getCurrentState(Game game) {
        gameContext.getCurrentState(game);
    }

    private boolean isValidUserId(Game game, String userId) {
        if (game.getUser1().equals(userId) || game.getUser2().equals(userId)) {
            return true;
        } else throw new IllegalArgumentException("User not found in game");
    }

    private String getUserIdByUserName(String userName) {
        User user = userRepository.getByUserName(userName);
        return user.getId();
    }
}
