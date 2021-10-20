package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.ShipType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class NumberOfCoordinatesValidator extends ShipValidator {

    //@Autowired
    public NumberOfCoordinatesValidator(/*@Qualifier("oneStraightLineValidator")*/ ShipValidator nextShipValidator) {
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
