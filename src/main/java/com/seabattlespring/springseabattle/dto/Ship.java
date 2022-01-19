package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seabattlespring.springseabattle.repository.domain.ShipType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Ship extends Cell{

    @JsonProperty("type")
    private ShipType shipType;

    @Valid
    @JsonProperty("cells")
    private List<com.seabattlespring.springseabattle.repository.domain.Cell> cells;

    @JsonCreator
    public Ship(ShipType shipType, List<com.seabattlespring.springseabattle.repository.domain.Cell> cells) {
        this.shipType = shipType;
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Ship";
    }
}
