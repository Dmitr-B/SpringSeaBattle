package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.dto.Shot;
import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameService {

    private final GameRepository gameRepository;

    public String createGame() {
        Game game = new Game();
        gameRepository.save(game);
        return game.getId();
    }

    public Game getGameById(String id) {
        return gameRepository.findGameById(id);
    }

    public void addShip(String id, FightField.Owner owner, Ship ship) {
        Game game = gameRepository.findGameById(id);
        //todo перевірити стан гри
        if (State.ARRANGEMENT.equals(game.getState())) {
            FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                    .filter(field -> owner.equals(field.getOwner()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("field not found"));

            ShipDto shipDto = new ShipDto();
            shipDto.setShipType(ship.getShipType());
            shipDto.setCells(ship.getCells());

            for (int i = 0; i < ship.getCells().size(); i++) {

                fightField.getCells().get(ship.getCells().get(i).getCoordinates().getX()).get(ship.getCells().get(i).getCoordinates().getY())
                        .setCellState(ship.getCells().get(i).getCellState());

            }

            fightField.getShips().add(shipDto);

            log.info("stateFight " + isFightStateGame(game));

            if (isFightStateGame(game)) {
                game.setState(State.FIGHT);
            }

            //todo перевіряти що гра готова і змінити стан гри

            gameRepository.save(game);
        }

    }

    public CellState shot(String id, FightField.Owner owner, Shot shot) {
        Game game = gameRepository.findGameById(id);
        //todo перевірити стан гри

        CellState newCellState = null;

        if (State.FIGHT.equals(game.getState())) {
            FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                    .filter(field -> !owner.equals(field.getOwner()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("field not found"));
            Cell cell = fightField.getCells().get(shot.getCoordinates().getX()).get(shot.getCoordinates().getY());

            //
            if (CellState.SHIP.equals(cell.getCellState())) {
                newCellState = resultOfTheShot(fightField, shot.getCoordinates());
            } else {
                newCellState = CellState.PAST;
            }
            cell.setCellState(newCellState);

            //todo чи гра завершена

            log.info("gameOver " + isGameOver(game));

            if (isGameOver(game)) {
                game.setState(State.OVER);
            }

            gameRepository.save(game);
        }


//
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

    private boolean isFightStateGame(Game game) {
        FightField fightField1 = game.getFightField1();
        FightField fightField2 = game.getFightField2();
        log.info("size1" + fightField1.getShips().size());
        log.info("size1" + fightField2.getShips().size());

        return fightField1.getShips().size() == 1 && fightField2.getShips().size() == 1;
    }

    private boolean isGameOver(Game game) {

        boolean overPlayer1 = game.getFightField1().getShips().stream()
                .filter(shipDto1 -> shipDto1.getCells().stream().anyMatch(cell -> cell.getCellState().equals(CellState.SHIP)))
                .findAny()
                .isEmpty();

        boolean overPlayer2 = game.getFightField2().getShips().stream()
                .filter(shipDto1 -> shipDto1.getCells().stream().anyMatch(cell -> cell.getCellState().equals(CellState.SHIP)))
                .findAny()
                .isEmpty();

        return overPlayer1 || overPlayer2;
    }
}
