package com.seabattlespring.springseabattle.game.validator.ship;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class NearbyCoordinatesValidator extends ShipValidator {

    public NearbyCoordinatesValidator(/*@Qualifier("numberOfValidShipTypeValidator")*/ ShipValidator nextShipValidator) {
        super(nextShipValidator);
    }

    @Override
    boolean isValid(FightField fightField, Ship ship) {
        int startX = ship.getCells().get(0).getCoordinates().getX();
        System.out.println("start " + startX);
        int startY = ship.getCells().get(0).getCoordinates().getY();
        System.out.println("start1 " + startY);

        for (int i = 0; i < ship.getCells().size(); i++) {
            if (Math.abs(ship.getCells().get(i).getCoordinates().getX() - startX) != i
                    && Math.abs(ship.getCells().get(i).getCoordinates().getY() - startY) != i)
                return false;
        }
        return true;
    }
}
