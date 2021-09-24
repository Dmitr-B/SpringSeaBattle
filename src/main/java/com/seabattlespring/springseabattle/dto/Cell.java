package com.seabattlespring.springseabattle.dto;

import com.seabattlespring.springseabattle.repository.domain.CellState;
import lombok.Data;

import javax.validation.Valid;

@Data
public abstract class Cell {

    private CellState cellState;

    @Valid
    private Coordinates coordinates;

    @Override
    public String toString() {
        return "Cell";
    }
}
