package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seabattlespring.springseabattle.game.BattleMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
//@Document
public class SingleDeckShip extends Ship{
    private static int counter = 0;

    //@Id
    //private String id = this.toString() + SingleDeckShip.getCounter();

    @JsonProperty("coordinates")
    private Coordinates coordinates;

//    @JsonProperty("x")
//    private int x;
//
//    @JsonProperty("y")
//    private int y;
    //private int[][] coordinates = new int[0][1];

    @JsonCreator
    public SingleDeckShip(Coordinates coordinates) {
        this.coordinates = coordinates;

        //if (counter < 4){
        //    counter++;
       // }

    }

    public static void setCounter(int counter) {
        SingleDeckShip.counter = counter;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "SingleDeckShip";
    }
}
