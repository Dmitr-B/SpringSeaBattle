package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SingleDeckShip extends Ship{
    private static int counter = 0;

    @JsonProperty("x")
    private int x;

    @JsonProperty("y")
    private int y;
    //private int[][] coordinates = new int[0][1];

    @JsonCreator
    public SingleDeckShip(int x, int y) {
        this.x = x;
        this.y = y;

        if (counter < 4)
            counter++;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "SingleDeckShip";
    }
}
