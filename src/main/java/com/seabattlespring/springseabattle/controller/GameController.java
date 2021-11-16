package com.seabattlespring.springseabattle.controller;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.dto.Shot;
import com.seabattlespring.springseabattle.game.validator.ship.exception.*;
import com.seabattlespring.springseabattle.game.validator.shot.exception.ShotException;
import com.seabattlespring.springseabattle.repository.UserRepository;
import com.seabattlespring.springseabattle.repository.domain.CellState;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.User;
import com.seabattlespring.springseabattle.service.GameService;
import com.seabattlespring.springseabattle.service.ShipService;
import com.seabattlespring.springseabattle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    private final UserService userService;

    @GetMapping("/welcome")
    public String index() {
        return "welcome";
    }

    @GetMapping()
    public String getCreatePage() {
        return "game";
    }

    @PostMapping
    //@PreAuthorize("hasAuthority('players:read')")
    public ResponseEntity<String> createGame(Authentication authentication) {
        String id = gameService.createGame(authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PatchMapping("/{id}/join")
    public ResponseEntity<Void> joinId(Authentication authentication, @PathVariable("id") String id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        gameService.joinGameById(id, authentication.getName());

        log.info("User " + authentication.getName() + " is joining to game: " + id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> getUserNameFromToken(Authentication authentication) {
        String name = authentication.getName();

        return ResponseEntity.ok(name);
    }

    @PatchMapping("/join")
    public ResponseEntity<Void> joinRandom(Authentication authentication) {

        gameService.joinToRandomGame(authentication.getName());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('players:read')")
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
    //@PreAuthorize("hasAuthority('players:write')")
    public ResponseEntity<Void> addShip(@PathVariable("id") String id, @PathVariable("owner") FightField.Owner owner,
                                        @Valid @RequestBody Ship ship, Authentication authentication,
                                        BindingResult bindingResult) throws NearbyCoordinatesException,
            CellEmptyException, NumberOfCoordinatesException, NumberOfValidShipException, OneStraightLineException, PlayerException {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

            gameService.addShip(id, authentication.getName(), owner, ship);

        log.info(owner + " is adding a new ship");
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/fight_field/{owner}/shot")
    //@PreAuthorize("hasAuthority('players:write')")
    public ResponseEntity<CellState> shot(@PathVariable("id") String id, @PathVariable("owner") FightField.Owner owner,
                                          Authentication authentication,
                                          @Valid @RequestBody Shot shot, BindingResult bindingResult) throws ShotException, PlayerException {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        CellState state = gameService.shot(id, authentication.getName(), owner, shot);

        log.info(owner + " made  shot");
        return ResponseEntity.ok(state);
    }

}
