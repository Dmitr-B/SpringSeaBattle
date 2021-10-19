package com.seabattlespring.springseabattle.game.validator.shot;

import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.Game;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ShotValidator {

    public boolean valid(Game game, FightField fightField) {

        switch (game.getState()) {
            case PLAYER1TURN:
                if (FightField.Owner.PLAYER2.equals(fightField.getOwner())) {
                    log.info("shooot1 " + fightField.getOwner());
                    return true;
                }
                break;
            case PLAYER2TURN:
                if (FightField.Owner.PLAYER1.equals(fightField.getOwner())) {
                    log.info("shooot2 " + fightField.getOwner());
                    return true;
                }
                break;
        }
        return false;
    }
}