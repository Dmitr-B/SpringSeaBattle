package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Cell;
import com.seabattlespring.springseabattle.dto.DoubleDeckShip;
import com.seabattlespring.springseabattle.dto.SingleDeckShip;
import com.seabattlespring.springseabattle.game.Game;
import com.seabattlespring.springseabattle.game.Player;
import com.seabattlespring.springseabattle.repository.SingleRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("Condon " + SingleDeckShip.getCounter());
    }


    public void saveSingleShip(SingleDeckShip singleDeckShip) {

        //if (game.getPlayer1().isEmptySinglePlace(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY())) {
            singleRepository.save(singleDeckShip);
        //    game.getPlayer1().getBattleMap().setOnceShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY(), "SingleDeckShip1");
        //    System.out.println(game.getPlayer1().getBattleMap().getOnceShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY()));
            //game.getPlayer1().getBattleMap().setArea(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY());
       // }

        //singleDeckShips.add(singleDeckShip);
        //log.info("Ship" + singleDeckShips);
        log.info("counter" + SingleDeckShip.getCounter());
//        System.out.println("coord " + singleDeckShips.get(0).getCoordinates());
//        System.out.println(player.getName());
    }

    public void addSingleShipToMap(SingleDeckShip singleDeckShip) {
        System.out.println("Penis " + game.getPlayer1().isValidSingleShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY()));
        if (game.getPlayer1().isValidSingleShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY())) {
            game.getPlayer1().getBattleMap().setOnceShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY(), singleDeckShip);
            SingleDeckShip.setCounter(SingleDeckShip.getCounter() + 1);
            log.info("counter" + SingleDeckShip.getCounter());
        };
    }

    public Cell getCell(int x, int y) {
        return game.getPlayer1().getBattleMap().getOnceShip(x, y);
    }

    public void addDoubleShipToMap(DoubleDeckShip doubleDeckShip) {
        log.info("Suchka " + doubleDeckShip);
    }
}
