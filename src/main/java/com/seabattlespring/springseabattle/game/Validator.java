package com.seabattlespring.springseabattle.game;

import com.seabattlespring.springseabattle.repository.domain.ShipDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
//@Component
public class Validator {

    public boolean isValidShip(ShipDto shipDto) {

        switch (shipDto.getShipType()) {
            case SINGLE:
                return isValidSingleShip(shipDto);
            //break;
            case DOUBLE:
                //return isValidDoubleShip(shipDto);
            break;
            case THREE:
                ///return isValidThreeShip(shipDto);
            break;
            case FOUR:
                //return isValidFourShip(shipDto);
            break;
        }

        return false;
    }

    private boolean isValidSingleShip(ShipDto shipDto) {
        return true;
    }
}
