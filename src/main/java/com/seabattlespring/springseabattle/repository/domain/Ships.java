package com.seabattlespring.springseabattle.repository.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Ships extends Cell {

    @JsonProperty("type")
    private ShipType shipType;

    private List<Cell> cells;
}
