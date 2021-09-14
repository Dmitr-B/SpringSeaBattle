package com.seabattlespring.springseabattle.controller;

import com.seabattlespring.springseabattle.dto.DoubleDeckShip;
import com.seabattlespring.springseabattle.dto.SingleDeckShip;
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

    @GetMapping("/play")
    //@ResponseBody
    public String index() {
        return "play";
    }

    @PostMapping()
    public ResponseEntity<SingleDeckShip> saveSingleShip(@RequestBody SingleDeckShip singleDeckShip) {

        if (singleDeckShip == null) {
            return ResponseEntity.notFound().build();
        }

        //this.shipService.saveSingleShip(singleDeckShip);
        if (SingleDeckShip.getCounter() != 4) {
            this.shipService.addSingleShipToMap(singleDeckShip);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(singleDeckShip);
    }

    @PostMapping("/double")
    public ResponseEntity<DoubleDeckShip> saveDoubleShip(@RequestBody DoubleDeckShip doubleDeckShip) {

        if (doubleDeckShip == null) {
            return ResponseEntity.notFound().build();
        }

        this.shipService.addDoubleShipToMap(doubleDeckShip);
        return ResponseEntity.status(HttpStatus.CREATED).body(doubleDeckShip);
    }

}
