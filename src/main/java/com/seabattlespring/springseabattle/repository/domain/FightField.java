package com.seabattlespring.springseabattle.repository.domain;

import com.seabattlespring.springseabattle.dto.Coordinates;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class FightField {

    private Owner owner;
    private List<List<Cell>> cells;
    private List<ShipDto> ships = /*Collections.emptyList()*/new ArrayList<>();

    public FightField(Owner owner) {
        this.owner = owner;
        cells = new ArrayList<>(10);

        for (int x = 0; x <= 9; x++) {
            cells.add(new ArrayList<>(10));
            for (int y = 0; y <= 9; y++) {
                cells.get(x).add(new Cell(CellState.EMPTY, new Coordinates(x,y)));
            }
        }
    }

    public enum Owner {
        PLAYER1, PLAYER2
    }
}
