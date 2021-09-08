package com.seabattlespring.springseabattle.game;

import com.seabattlespring.springseabattle.dto.Area;
import com.seabattlespring.springseabattle.dto.Ship;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class BattleMap {
    private Ship[][] shipsOnMap;

    public BattleMap() {
        shipsOnMap = new Ship[10][10];
    }

    private void setArea(int x, int y) {
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0: if (x + 1 < shipsOnMap.length)
                    if (getOnceShip(x + 1, y) == null)
                        shipsOnMap[x+1][y] = createArea();
                    break;
                case 1: if (x + 1 < shipsOnMap.length && y + 1 < shipsOnMap.length)
                    if (getOnceShip(x + 1, y + 1) == null)
                        shipsOnMap[x+1][y+1] = createArea();
                    break;
                case 2: if (y + 1 < shipsOnMap.length)
                    if (getOnceShip(x, y + 1) == null)
                        shipsOnMap[x][y+1] = createArea();
                    break;
                case 3: if (x - 1 >= 0)
                    if (getOnceShip(x - 1, y) == null)
                        shipsOnMap[x-1][y] = createArea();
                    break;
                case 4: if (x - 1 >= 0 && y + 1 < shipsOnMap.length)
                    if (getOnceShip(x - 1, y + 1) == null)
                        shipsOnMap[x-1][y+1] = createArea();
                    break;
                case 5: if (x - 1 >= 0 && y - 1 >= 0)
                    if (getOnceShip(x - 1, y - 1) == null)
                        shipsOnMap[x-1][y-1] = createArea();
                    break;
                case 6: if (y - 1 >= 0)
                    if (getOnceShip(x, y -1 ) == null)
                        shipsOnMap[x][y-1] = createArea();
                    break;
                case 7: if (x + 1 < shipsOnMap.length && y - 1 >= 0)
                    if (getOnceShip(x + 1, y - 1) == null)
                        shipsOnMap[x+1][y-1] = createArea();
                    break;
            }

        }
    }

    private Ship getOnceShip(int x, int y) {
        return shipsOnMap[x][y];
    }

    private Area createArea() {
        return new Area();
    }
}
