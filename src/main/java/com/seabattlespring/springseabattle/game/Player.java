package com.seabattlespring.springseabattle.game;

import com.seabattlespring.springseabattle.dto.SingleDeckShip;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Player {
    private String name;
    private BattleMap battleMap;

    public Player(String name) {
        this.name = name;
        this.battleMap = new BattleMap();
    }

    public boolean isEmptySinglePlace(int x, int y) {
        return battleMap.getOnceShip(x, y) == null;
    }

    public boolean isValidSingleShip(int x, int y) {
        return isEmptySinglePlace(x, y) && SingleDeckShip.getCounter() != 4;
    }
}
