package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.repository.domain.ShipDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShipsRepository extends MongoRepository<ShipDto, String> {

}
