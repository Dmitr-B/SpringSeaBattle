package com.seabattlespring.springseabattle.game.state;

import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.State;

import java.util.Random;

public class ArrangementState extends GameState{

    public ArrangementState(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    void doChangeGameState(Game game) {
        if (isArrangementComplete(game)) {
            Random random = new Random();
            int whoTurn = random.nextInt(1);
            switch (whoTurn) {
                case 0:
                    gameContext.changeGameState(new Player1TurnState(gameContext));
                    gameContext.doChangeGameState(game);
                    //game.setState(State.PLAYER1TURN);
                    break;
                case 1:
                    gameContext.changeGameState(new Player2TurnState(gameContext));
                    gameContext.doChangeGameState(game);
                    //game.setState(State.PLAYER2TURN);
                    break;
            }
        }
    }

    private boolean isArrangementComplete(Game game) {
        //todo change size of ships to 10
        FightField fightField1 = game.getFightField1();
        FightField fightField2 = game.getFightField2();
        //log.info("size1 " + fightField1.getShips().size());
        //log.info("size2 " + fightField2.getShips().size());

        return fightField1.getShips().size() == 1 && fightField2.getShips().size() == 1;
    }
}
