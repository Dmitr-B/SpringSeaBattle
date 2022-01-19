package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.FightField;

public class NearbyCoordinatesValidator extends ShipValidator {

    public NearbyCoordinatesValidator( ShipValidator nextShipValidator) {
        super(nextShipValidator);
    }

    @Override
    boolean isValid(FightField fightField, Ship ship) {
        int startX = ship.getCells().get(0).getCoordinates().getX();
        int startY = ship.getCells().get(0).getCoordinates().getY();

        for (int i = 0; i < ship.getCells().size(); i++) {
            if (Math.abs(ship.getCells().get(i).getCoordinates().getX() - startX) != i
                    && Math.abs(ship.getCells().get(i).getCoordinates().getY() - startY) != i)
                return false;
        }
        return true;
    }
}
