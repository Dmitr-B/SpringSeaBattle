package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.SingleDeckShip;
import com.seabattlespring.springseabattle.game.Game;
import com.seabattlespring.springseabattle.game.Player;
import com.seabattlespring.springseabattle.repository.SingleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ShipService {

    private final SingleRepository singleRepository;

    private Game game;

    {
        Player player = new Player("loh");
        Player player1 = new Player("Pidar");
        game = new Game(player,player1);
    }


    public void saveSingleShip(SingleDeckShip singleDeckShip) {

        if (game.getPlayer1().isEmptySinglePlace(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY())) {
            //game.getPlayer1().getBattleMap().setArea(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY());
        }
        singleRepository.save(singleDeckShip);
        //singleDeckShips.add(singleDeckShip);
        //log.info("Ship" + singleDeckShips);
        log.info("counter" + SingleDeckShip.getCounter());
//        System.out.println("coord " + singleDeckShips.get(0).getCoordinates());
//        System.out.println(player.getName());
    }
}
