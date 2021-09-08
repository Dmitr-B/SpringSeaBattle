package com.seabattlespring.springseabattle.dto;

public class ThreeDeckShip extends Ship{
    private static int counter = 0;

    public ThreeDeckShip() {

        if (counter < 2)
            counter++;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "ThreeDeckShip";
    }
}
