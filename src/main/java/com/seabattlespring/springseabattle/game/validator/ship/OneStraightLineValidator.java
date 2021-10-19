package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.FightField;

public class OneStraightLineValidator extends ShipValidator {
    public OneStraightLineValidator(ShipValidator nextShipValidator) {
        super(nextShipValidator);
    }

    @Override
    boolean isValid(FightField fightField, Ship ship) {
        int startX = ship.getCells().get(0).getCoordinates().getX();
        int startY = ship.getCells().get(0).getCoordinates().getY();

        for (int i = 0; i < ship.getCells().size(); i++) {
            if (startX != ship.getCells().get(i).getCoordinates().getX()
                    && startY != ship.getCells().get(i).getCoordinates().getY())
                return false;
        }
        return true;
    }
}
