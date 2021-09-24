package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
public class Shot {

    @JsonProperty("coordinates")
    @Valid
    private Coordinates coordinates;

    @JsonCreator
    public Shot(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
