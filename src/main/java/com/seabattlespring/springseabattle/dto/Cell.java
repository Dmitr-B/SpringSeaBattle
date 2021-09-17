package com.seabattlespring.springseabattle.dto;

import com.seabattlespring.springseabattle.repository.domain.CellState;
import lombok.Data;

@Data
public abstract class Cell {

    private CellState cellState;

    private Coordinates coordinates;

    @Override
    public String toString() {
        return "Cell";
    }
}
