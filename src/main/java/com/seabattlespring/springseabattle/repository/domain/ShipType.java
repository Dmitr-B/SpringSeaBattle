package com.seabattlespring.springseabattle.repository.domain;

public enum ShipType {
    SINGLE(1,4), DOUBLE(2,3), THREE(3,2), FOUR(4,1);

    private int size;
    private int number;

    ShipType(int size, int number) {
        this.size = size;
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public int getNumber() {
        return number;
    }
}
