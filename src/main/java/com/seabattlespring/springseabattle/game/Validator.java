package com.seabattlespring.springseabattle.game;

import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.repository.domain.Ships;
import com.seabattlespring.springseabattle.service.ShipService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
//@Component
public class Validator {

    public boolean isValidShip(Ships ships) {

        switch (ships.getShipType()) {
            case SINGLE:
                return isValidSingleShip(ships);
            //break;
            case DOUBLE:
                //return isValidDoubleShip(ships);
            break;
            case THREE:
                ///return isValidThreeShip(ships);
            break;
            case FOUR:
                //return isValidFourShip(ships);
            break;
        }

        return false;
    }

    private boolean isValidSingleShip(Ships ships) {
        return true;
    }
}
