package com.seabattlespring.springseabattle.controller;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.dto.Shot;
import com.seabattlespring.springseabattle.game.validator.ship.exception.*;
import com.seabattlespring.springseabattle.game.validator.shot.exception.ShotException;
import com.seabattlespring.springseabattle.repository.domain.CellState;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.service.GameService;
import com.seabattlespring.springseabattle.service.ShipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
//    public ResponseEntity<ShipDto> saveShips(@RequestBody ShipDto ships) {
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
    @PreAuthorize("hasAuthority('players:write')")
    public ResponseEntity<String> createGame() {
        String id = gameService.createGame();
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('players:read')")
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

    @PatchMapping("/{id}/fight_field/{owner}/ship")
    @PreAuthorize("hasAuthority('players:write')")
    public ResponseEntity<Void> addShip(@PathVariable("id") String id, @PathVariable("owner") FightField.Owner owner,
                                        @Valid @RequestBody Ship ship, BindingResult bindingResult) throws NearbyCoordinatesException,
            CellEmptyException, NumberOfCoordinatesException, NumberOfValidShipException, OneStraightLineException {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

            gameService.addShip(id, owner, ship);


        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/fight_field/{owner}/shot")
    @PreAuthorize("hasAuthority('players:write')")
    public ResponseEntity<CellState> shot(@PathVariable("id") String id, @PathVariable("owner") FightField.Owner owner,
                                          @Valid @RequestBody Shot shot, BindingResult bindingResult) throws ShotException {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        CellState state = gameService.shot(id, owner, shot);

        return ResponseEntity.ok(state);
    }

}
