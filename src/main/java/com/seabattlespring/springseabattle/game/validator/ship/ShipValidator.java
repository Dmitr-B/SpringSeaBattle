package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.FightField;

public abstract class ShipValidator {
    //todo добавить валідатор чи той гравець ходе
    private final ShipValidator nextShipValidator;

    public ShipValidator(ShipValidator nextShipValidator) {
        this.nextShipValidator = nextShipValidator;
    }

    public boolean valid(FightField fightField, Ship ship) {
        boolean isValid = isValid(fightField, ship);
        boolean nextValid = true;

        if (nextShipValidator != null) {
            nextValid = nextShipValidator.valid(fightField, ship);
        }
        return isValid && nextValid;
    }

    abstract boolean isValid(FightField fightField, Ship ship);
}
