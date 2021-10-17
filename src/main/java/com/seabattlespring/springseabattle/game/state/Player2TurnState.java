package com.seabattlespring.springseabattle.game.state;

import com.seabattlespring.springseabattle.repository.domain.CellState;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.State;

public class Player2TurnState extends GameState{

    public Player2TurnState(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    void doChangeGameState(Game game) {
        game.setState(State.PLAYER2TURN);
        if (isPlayer2Win(game)) {
            gameContext.changeGameState(new OverState(gameContext));
            gameContext.doChangeGameState(game);
            //game.setState(State.OVER);
        }
    }

    private boolean isPlayer2Win(Game game) {

        return game.getFightField1().getShips().stream()
                .filter(shipDto1 -> shipDto1.getCells().stream().anyMatch(cell -> cell.getCellState().equals(CellState.SHIP)))
                .findAny()
                .isEmpty();
    }
}
