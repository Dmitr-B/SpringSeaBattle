package com.seabattlespring.springseabattle.game.state;

import com.seabattlespring.springseabattle.repository.domain.Game;

public class GameContext {
    private GameState gameState;

//    public void setGameState(GameState gameState) {
//        this.gameState = gameState;
//    }

    public GameContext() {
        //getCurrentState(game);
    }

    public void changeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getCurrentState(Game game) {
        switch (game.getState()) {
            case ARRANGEMENT:
                return gameState = new ArrangementState(this);
            case PLAYER1TURN:
                return gameState = new Player1TurnState(this);
            case PLAYER2TURN:
                return gameState = new Player2TurnState(this);
            case OVER:
                return gameState = new OverState(this);
        }
        return null;
    }

    public void doChangeGameState(Game game) {
        gameState.doChangeGameState(game);
    }

    public GameState getGameState() {
        return gameState;
    }
}
