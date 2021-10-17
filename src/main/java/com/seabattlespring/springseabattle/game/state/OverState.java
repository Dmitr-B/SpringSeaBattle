package com.seabattlespring.springseabattle.game.state;

import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.State;

public class OverState extends GameState{

    public OverState(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    void doChangeGameState(Game game) {
        game.setState(State.OVER);
    }
}
