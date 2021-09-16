package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.repository.domain.FightField;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FightFieldRepository extends MongoRepository<FightField, String> {

}
