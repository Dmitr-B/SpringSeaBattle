package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.repository.domain.Cell;
import com.seabattlespring.springseabattle.repository.domain.Game;
import com.seabattlespring.springseabattle.repository.domain.Ships;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {

    Game findGameById(String id);
}
