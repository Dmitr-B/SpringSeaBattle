package com.seabattlespring.springseabattle.game.state;

import com.seabattlespring.springseabattle.repository.domain.CellState;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.State;

public class Player1TurnState extends GameState{

    public Player1TurnState(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    void doChangeGameState(Game game) {
        game.setState(State.PLAYER1TURN);
        if (isPlayer1Win(game)) {
            gameContext.changeGameState(new OverState(gameContext));
            gameContext.doChangeGameState(game);
            game.setWinner(FightField.Owner.PLAYER1.name());
            //game.setState(State.OVER);
        }
    }

    private boolean isPlayer1Win(Game game) {

        return game.getFightField2().getShips().stream()
                .filter(shipDto1 -> shipDto1.getCells().stream().anyMatch(cell -> cell.getCellState().equals(CellState.SHIP)))
                .findAny()
                .isEmpty();
    }
}
