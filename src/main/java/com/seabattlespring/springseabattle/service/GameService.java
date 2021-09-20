package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.dto.Shot;
import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.ShipsRepository;
import com.seabattlespring.springseabattle.repository.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameService {

    private final GameRepository gameRepository;
    //private final ShipsRepository shipsRepository;

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
        FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                .filter(field -> owner.equals(field.getOwner()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("field not found"));

        //todo Ship(Controller layer) -> Ships(Model Layer)
        Ships ships = new Ships();
        ships.setShipType(ship.getShipType());
        ships.setCells(ship.getCells());

        for (int i = 0; i < ship.getCells().size(); i++) {

            fightField.getCells().get(ship.getCells().get(i).getCoordinates().getX()).get(ship.getCells().get(i).getCoordinates().getY())
                    .setCellState(ship.getCells().get(i).getCellState());

        }

        fightField.getShips().add(ships);

        gameRepository.save(game);

        //todo зберігти дані в БД
    }

    public CellState shot(String id, FightField.Owner owner, Shot shot) {
        Game game = gameRepository.findGameById(id);
        FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                .filter(field -> !owner.equals(field.getOwner()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("field not found"));

        com.seabattlespring.springseabattle.repository.domain.Shot shot1 = new com.seabattlespring.springseabattle.repository.domain.Shot();
        shot1.setCoordinates(shot.getCoordinates());

        switch (fightField.getCells().get(shot1.getCoordinates().getX()).get(shot1.getCoordinates().getY()).getCellState()) {

            case SHIP:
                //return CellState.KNOCKED;
                return resultOfShot(game, fightField, shot1.getCoordinates());
            case KNOCKED:
                return CellState.SUNK;
        }
        return CellState.PAST;
    }

    //private ShipType getShipType(/*String id,*/ FightField fightField, Coordinates coordinates) {
        //Game game = gameRepository.findGameById(id);
        //fightField.getCells().get(coordinates.getX()).get(coordinates.getY()).getCellState();
        //Ships ships = shipsRepository.findByCellsContains(coordinates);
        //Ships ships = shipsRepository.findShipsByCellsContaining(coordinates);
        //return ships.getShipType();
   // }

    private CellState resultOfShot(Game game, FightField fightField, Coordinates coordinates) {

//        Ships ships = new Ships();
//
//        for (Ships ship:fightField.getShips()) {
//            for (Cell cell: ship.getCells()) {
//                if (cell.getCoordinates().equals(coordinates)) {
//                    ships = ship;
//                    break;
//                }
//            }
//        }

        Ships ships = fightField.getShips().stream()
                .filter(s -> s.getCells().stream().anyMatch(cell -> cell.getCoordinates().equals(coordinates)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ship not found"));

        long count = ships.getCells().stream()
                .filter(ship -> ship.getCellState().equals(CellState.SHIP))
                .count();

        if (count == 1) {

            ships.getCells()
                    .forEach(sunkCells -> sunkCells.setCellState(CellState.SUNK));
            log.info("loh " + ships);
            return CellState.SUNK;
        }
            //fightField.getCells().forEach(cells -> cells.forEach(cell -> cell.getCoordinates().));


        fightField.getCells().get(coordinates.getX()).get(coordinates.getY()).setCellState(CellState.KNOCKED);
        //log.info("chlen " + fightField);
        ships.getCells().stream()
                .filter(knockedCell -> knockedCell.getCoordinates().equals(coordinates))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("cell not found"))
                .setCellState(CellState.KNOCKED);
        //log.info("zalupa " + ships);
        gameRepository.save(game);
        return CellState.KNOCKED;
    }
}
