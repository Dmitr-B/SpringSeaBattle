package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.dto.Ship;
import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.domain.FightField;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.Ships;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameService {

    private final GameRepository gameRepository;

    public String createGame() {
        Game game = new Game();
        gameRepository.save(game);
        return game.getId();
    }

    public Game getGameById(String id) {
        return gameRepository.findGameById(id);
    }

    public void addShip(String id, FightField.Owner owner, Ship ship) {
        Game game = gameRepository.findGameById(id);
        FightField fightField = Stream.of(game.getFightField1(), game.getFightField2())
                .filter(field -> owner.equals(field.getOwner()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("field not found"));

        //todo Ship(Controller layer) -> Ships(Model Layer)
        Ships ships = new Ships();
        //ships.setShipType();
        //ships.setCells();

        fightField.getShips().add(ships);

        //todo зберігти дані в БД
    }
}
