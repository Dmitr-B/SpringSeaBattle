package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Cell;
//import com.seabattlespring.springseabattle.dto.DoubleDeckShip;
//import com.seabattlespring.springseabattle.dto.SingleDeckShip;
//import com.seabattlespring.springseabattle.game.Game;
//import com.seabattlespring.springseabattle.game.Player;
import com.seabattlespring.springseabattle.repository.ShipsRepository;
import com.seabattlespring.springseabattle.repository.domain.ShipType;
import com.seabattlespring.springseabattle.repository.domain.Ships;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ShipService {

    private final ShipsRepository shipsRepository;
    //private final SingleRepository singleRepository;

    //private Game game;

//    {
//        Player player = new Player("loh");
//        Player player1 = new Player("Pidar");
//        game = new Game(player,player1);
//        //System.out.println(this.getAllShips());
//    }


//    public void saveSingleShip(SingleDeckShip singleDeckShip) {
//
//        //if (game.getPlayer1().isEmptySinglePlace(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY())) {
//            //singleRepository.save(singleDeckShip);
//            //shipsRepository.save(singleDeckShip);
//        //    game.getPlayer1().getBattleMap().setOnceShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY(), "SingleDeckShip1");
//        //    System.out.println(game.getPlayer1().getBattleMap().getOnceShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY()));
//            //game.getPlayer1().getBattleMap().setArea(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY());
//       // }
//
//        //singleDeckShips.add(singleDeckShip);
//        //log.info("Ship" + singleDeckShips);
//        log.info("counter" + SingleDeckShip.getCounter());
////        System.out.println("coord " + singleDeckShips.get(0).getCoordinates());
////        System.out.println(player.getName());
//    }

    public void saveShip(Ships ships) {
        if (ships.getShipType().equals(ShipType.SINGLE))
        shipsRepository.save(ships);
    }

    public List<Ships> getAllShips() {
        return shipsRepository.findAll();
    }

//    public void addSingleShipToMap(SingleDeckShip singleDeckShip) {
//        System.out.println("Penis " + game.getPlayer1().isValidSingleShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY()));
//        if (game.getPlayer1().isValidSingleShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY())) {
//            game.getPlayer1().getBattleMap().setOnceShip(singleDeckShip.getCoordinates().getX(), singleDeckShip.getCoordinates().getY(), singleDeckShip);
//            SingleDeckShip.setCounter(SingleDeckShip.getCounter() + 1);
//            log.info("counter" + SingleDeckShip.getCounter());
//        };
//    }

    //public Cell getCell(int x, int y) {
    //    return game.getPlayer1().getBattleMap().getOnceShip(x, y);
   // }

//    public void addDoubleShipToMap(DoubleDeckShip doubleDeckShip) {
//        log.info("Fuck " + doubleDeckShip);
//        log.info("x " + doubleDeckShip.getCoordinates1().getX());
//        log.info("y " + doubleDeckShip.getCoordinates1().getY());
//        log.info("x1 " + doubleDeckShip.getCoordinates2().getX());
//        log.info("y1 " + doubleDeckShip.getCoordinates2().getY());
//        log.info("Valid input " + game.getPlayer1().isValidDoubleCoordinates(doubleDeckShip.getCoordinates1().getX(), doubleDeckShip.getCoordinates1().getY(),
//                doubleDeckShip.getCoordinates2().getX(), doubleDeckShip.getCoordinates2().getY()));
//    }
}
