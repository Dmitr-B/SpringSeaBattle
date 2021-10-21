package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.exception.*;
import com.seabattlespring.springseabattle.repository.domain.FightField;

public abstract class ShipValidator {
    //todo добавить валідатор чи той гравець ходе
    private final ShipValidator nextShipValidator;

    public ShipValidator(ShipValidator nextShipValidator) {
        this.nextShipValidator = nextShipValidator;
    }

    public boolean valid(FightField fightField, Ship ship) throws NumberOfCoordinatesException, OneStraightLineException,
            NearbyCoordinatesException, NumberOfValidShipException, CellEmptyException {
        boolean isValid = isValid(fightField, ship);
        boolean nextValid = true;

        if (nextShipValidator != null && isValid) {
            nextValid = nextShipValidator.valid(fightField, ship);
        }

        if (!isValid && nextShipValidator instanceof OneStraightLineValidator)
            throw new NumberOfCoordinatesException("Mismatch in the number of coordinates with the decks");

        if (!isValid && nextShipValidator instanceof NearbyCoordinatesValidator)
            throw new OneStraightLineException("The ship must be horizontal or vertical");

        if (!isValid && nextShipValidator instanceof NumberOfValidShipTypeValidator)
            throw new NearbyCoordinatesException("The deck of the ship is not nearby");

        if (!isValid && nextShipValidator instanceof CellEmptyValidator)
            throw new NumberOfValidShipException("The maximum number of ships of a certain type has been reached");

        if (!isValid && nextShipValidator == null)
            throw new CellEmptyException("The cell is already busy");

        return isValid && nextValid;
    }

    abstract boolean isValid(FightField fightField, Ship ship);
}
