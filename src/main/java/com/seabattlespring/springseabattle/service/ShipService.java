package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.SingleDeckShip;
import com.seabattlespring.springseabattle.game.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ShipService {

    private Player player;
    private List<SingleDeckShip> singleDeckShips;

    {
        singleDeckShips = new ArrayList<>();
        player = new Player("loh");
    }


    public void saveSingleShip(SingleDeckShip singleDeckShip) {
//        if (player.isEmptySinglePlace(singleDeckShip.getX(), singleDeckShip.getY())) {
//            player.getBattleMap().getOnceShip(singleDeckShip.getX(),singleDeckShip.getY())
//        }
        singleDeckShips.add(singleDeckShip);
        log.info("Ship" + singleDeckShips);
        log.info("counter" + SingleDeckShip.getCounter());
        System.out.println(player.getName());
    }
}
