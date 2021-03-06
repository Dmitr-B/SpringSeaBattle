package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.ShipType;

public class NumberOfValidShipTypeValidator extends ShipValidator {

    public NumberOfValidShipTypeValidator(ShipValidator nextShipValidator) {
        super(nextShipValidator);
    }

    @Override
    boolean isValid(FightField fightField, Ship ship) {

        switch (ship.getShipType()) {
            case SINGLE:
                return fightField.getShips().stream().filter(shipDto -> shipDto.getShipType().equals(ship.getShipType())).count() < ShipType.SINGLE.getNumber();
            case DOUBLE:
                return fightField.getShips().stream().filter(shipDto -> shipDto.getShipType().equals(ship.getShipType())).count() < ShipType.DOUBLE.getNumber();
            case THREE:
                return fightField.getShips().stream().filter(shipDto -> shipDto.getShipType().equals(ship.getShipType())).count() < ShipType.THREE.getNumber();
            case FOUR:
                return fightField.getShips().stream().filter(shipDto -> shipDto.getShipType().equals(ship.getShipType())).count() < ShipType.FOUR.getNumber();
        }
        return false;
    }
}
