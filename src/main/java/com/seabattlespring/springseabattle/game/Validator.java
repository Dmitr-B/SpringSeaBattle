package com.seabattlespring.springseabattle.game;

import com.seabattlespring.springseabattle.repository.domain.ShipDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
//@Component
public class Validator {
    //todo валідація
    //1 кількість координат
    //2 вірність координат валідність (від 0 до 9)
    //3 чи гра доступна
    //кількість існуючих кораблів на кожен тип
    //чи знаїодяться координати по одній прямій і поруч
    //чи вільна ячейка для розміщення корабля

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
