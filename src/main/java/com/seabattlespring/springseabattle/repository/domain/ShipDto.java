package com.seabattlespring.springseabattle.repository.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ShipDto extends Cell {

    @JsonProperty("type")
    private ShipType shipType;

    private List<Cell> cells;
}
