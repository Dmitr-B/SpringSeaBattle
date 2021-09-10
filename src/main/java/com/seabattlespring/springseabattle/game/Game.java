package com.seabattlespring.springseabattle.game;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Game {

    private Player player1;
    private Player player2;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }
}
