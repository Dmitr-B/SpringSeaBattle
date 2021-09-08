package com.seabattlespring.springseabattle.game;

import lombok.Data;

@Data
public class Player {
    private String name;
    private BattleMap battleMap;

    public Player(String name) {
        this.name = name;
        this.battleMap = new BattleMap();
    }
}
