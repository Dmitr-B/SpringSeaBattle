package com.seabattlespring.springseabattle.game.validator;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.ShipType;

public class NumberOfCoordinatesValidator extends ShipValidator{
    public NumberOfCoordinatesValidator(ShipValidator nextShipValidator) {
        super(nextShipValidator);
    }

    @Override
    boolean isValid(FightField fightField, Ship ship) {

        switch (ship.getShipType()) {
            case SINGLE:
                return ShipType.SINGLE.getSize() == ship.getCells().size();
            case DOUBLE:
                return ShipType.DOUBLE.getSize() == ship.getCells().size();
            case THREE:
                return ShipType.THREE.getSize() == ship.getCells().size();
            case FOUR:
                return ShipType.FOUR.getSize() == ship.getCells().size();
        }
        return false;
    }
}
