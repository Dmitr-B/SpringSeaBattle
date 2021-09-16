package com.seabattlespring.springseabattle.controller;

import com.seabattlespring.springseabattle.dto.DoubleDeckShip;
import com.seabattlespring.springseabattle.dto.SingleDeckShip;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.Ships;
import com.seabattlespring.springseabattle.service.GameService;
import com.seabattlespring.springseabattle.service.ShipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final ShipService shipService;
    private final GameService gameService;

    @GetMapping("/play")
    //@ResponseBody
    public String index() {
        return "play";
    }

//    @PostMapping()
//    public ResponseEntity<SingleDeckShip> saveSingleShip(@RequestBody SingleDeckShip singleDeckShip) {
//
//        if (singleDeckShip == null) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        //this.shipService.saveSingleShip(singleDeckShip);
//        if (SingleDeckShip.getCounter() != 4) {
//            this.shipService.addSingleShipToMap(singleDeckShip);
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(singleDeckShip);
//    }

//    @PostMapping()
//    public ResponseEntity<Ships> saveShips(@RequestBody Ships ships) {
//
//        if (ships == null) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        this.shipService.saveShip(ships);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(ships);
//    }

//    @PostMapping("/double")
//    public ResponseEntity<DoubleDeckShip> saveDoubleShip(@RequestBody DoubleDeckShip doubleDeckShip) {
//
//        if (doubleDeckShip == null) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        this.shipService.addDoubleShipToMap(doubleDeckShip);
//        return ResponseEntity.status(HttpStatus.CREATED).body(doubleDeckShip);
//    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {

        if (game == null) {
            return ResponseEntity.badRequest().build();
        }

        this.gameService.saveGame(game);

        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable("id") String id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Game game = gameService.getGameById(id);

        if (game == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(game);
    }

}
