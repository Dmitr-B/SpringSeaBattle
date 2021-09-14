package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class DoubleDeckShip extends Ship{
    private static int counter = 0;

    @Id
    private String id = this.toString() + DoubleDeckShip.getCounter();

    @JsonProperty("coordinates1")
    private Coordinates coordinates1;

    @JsonProperty("coordinates2")
    private Coordinates coordinates2;

    @JsonCreator
    public DoubleDeckShip(Coordinates coordinates1, Coordinates coordinates2) {
        this.coordinates1 = coordinates1;
        this.coordinates2 = coordinates2;

        //if (counter < 3)
       //     counter++;
    }

    public void setCounter(int counter) {
        DoubleDeckShip.counter = counter;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "DoubleDeckShip";
    }
}
