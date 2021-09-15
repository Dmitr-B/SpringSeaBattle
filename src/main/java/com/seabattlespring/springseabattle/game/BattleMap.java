package com.seabattlespring.springseabattle.game;

import com.seabattlespring.springseabattle.dto.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
public class BattleMap {
    private Cell[][] shipsOnMap;
    private Set<SingleDeckShip> singleDeckShips;
    //private SingleRepository singleRepository;

    public BattleMap() {
        shipsOnMap = new Cell[10][10];
    }

    public void setOnceShip(int x, int y, SingleDeckShip singleDeckShip) {
        shipsOnMap[x][y] = singleDeckShip;
        setArea(x, y);
    }

    public void setArea(int x, int y) {

        if (x + 1 < shipsOnMap.length) {
            if (getOnceShip(x + 1, y) == null)
                shipsOnMap[x+1][y] = createArea();
        }

        if (x + 1 < shipsOnMap.length && y + 1 < shipsOnMap.length) {
            if (getOnceShip(x + 1, y + 1) == null)
                shipsOnMap[x+1][y+1] = createArea();
        }

        if (y + 1 < shipsOnMap.length) {
            if (getOnceShip(x, y + 1) == null)
                shipsOnMap[x][y+1] = createArea();
        }

        if (x - 1 >= 0) {
            if (getOnceShip(x - 1, y) == null)
                shipsOnMap[x-1][y] = createArea();
        }

        if (x - 1 >= 0 && y + 1 < shipsOnMap.length) {
            if (getOnceShip(x - 1, y + 1) == null)
                shipsOnMap[x-1][y+1] = createArea();
        }

        if (x - 1 >= 0 && y - 1 >= 0) {
            if (getOnceShip(x - 1, y - 1) == null)
                shipsOnMap[x-1][y-1] = createArea();
        }

        if (y - 1 >= 0) {
            if (getOnceShip(x, y -1 ) == null)
                shipsOnMap[x][y-1] = createArea();
        }

        if (x + 1 < shipsOnMap.length && y - 1 >= 0) {
            if (getOnceShip(x + 1, y - 1) == null)
                shipsOnMap[x+1][y-1] = createArea();
        }
    }

    public Cell getOnceShip(int x, int y) {
        return shipsOnMap[x][y];
    }

    private Area createArea() {
        return new Area();
    }
}
