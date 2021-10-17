package com.seabattlespring.springseabattle.game.state;

import com.seabattlespring.springseabattle.repository.domain.Game;

public abstract class GameState {
    GameContext gameContext;

    public GameState(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    abstract void doChangeGameState(Game game);
}
