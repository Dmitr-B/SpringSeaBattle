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
    //1 вірність координат валідність (від 0 до 9) (success)
    //2 кількість координат (success)
    //3 чи знаходяться координати по одній прямій (success)
    //4 чи знаходяться координати поруч (success)
    //5 кількість існуючих кораблів на кожен тип (success)
    //6 чи вільна ячейка для розміщення корабля (success)
    //7 чи гра доступна

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
