package com.seabattlespring.springseabattle.dto;

public class SingleDeckShip extends Ship{
    private static int counter = 0;

    public SingleDeckShip() {

        if (counter < 4)
            counter++;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "SingleDeckShip{}";
    }
}
