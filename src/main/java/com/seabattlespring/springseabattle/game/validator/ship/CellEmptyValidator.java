package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.CellState;
import com.seabattlespring.springseabattle.repository.domain.FightField;

public class CellEmptyValidator extends ShipValidator {

    public CellEmptyValidator(ShipValidator nextShipValidator) {
        super(nextShipValidator);
    }

    @Override
    boolean isValid(FightField fightField, Ship ship) {

        for (int i = 0; i < ship.getCells().size(); i++) {
            if (!CellState.EMPTY.equals(fightField.getCells().get(ship.getCells().get(i).getCoordinates().getX()).
                    get(ship.getCells().get(i).getCoordinates().getY()).getCellState())) {
                return false;
            }
        }
        return true;
    }
}
