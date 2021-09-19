package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Shot {

    @JsonProperty("coordinates")
    private Coordinates coordinates;

    @JsonCreator
    public Shot(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
