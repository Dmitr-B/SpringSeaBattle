package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.dto.SingleDeckShip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SingleRepository extends MongoRepository<SingleDeckShip, Integer> {

}
