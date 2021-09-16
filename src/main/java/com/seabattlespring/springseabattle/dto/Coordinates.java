package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinates {

    //@JsonProperty("x")
    private int x;
    //@JsonProperty("y")
    private int y;

//    @JsonCreator
//    public Coordinates (int x, int y) {
//        this.x = x;
//        this.y = y;
//    }
}
