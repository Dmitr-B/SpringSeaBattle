package com.seabattlespring.springseabattle.repository.domain;

import com.seabattlespring.springseabattle.dto.Coordinates;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
//@NoArgsConstructor
//@Document(collection = "fightField")
public class FightField {

//    @Id
//    private String id;

    private Owner owner;
    private List<List<Cell>> cells;
    private List<ShipDto> ships = Collections.emptyList();

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
// [0] [1] [2] [3]
// |
// [0]
// [1]
// [2]
}
