package com.seabattlespring.springseabattle.dto;

public class DoubleDeckShip extends Ship{
    private static int counter = 0;

    public DoubleDeckShip() {

        if (counter < 3)
            counter++;
    }

    public static int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "DoubleDeckShip";
    }
}
