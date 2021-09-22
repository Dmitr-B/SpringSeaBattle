package com.seabattlespring.springseabattle.game;

import com.seabattlespring.springseabattle.repository.domain.ShipDto;
import com.seabattlespring.springseabattle.repository.domain.ShipType;
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

    public boolean isValidNumberOfCoordinates(ShipDto shipDto) {
        return ShipType.SINGLE.getNumber() == shipDto.getCells().size() || ShipType.DOUBLE.getNumber() == shipDto.getCells().size()
                || ShipType.THREE.getNumber() == shipDto.getCells().size() || ShipType.FOUR.getNumber() == shipDto.getCells().size();
    }
}
