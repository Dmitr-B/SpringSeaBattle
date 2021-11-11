package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.repository.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {

    Game findGameById(String id);
    List<Game> findAllByUser1IsNotNull();
}
