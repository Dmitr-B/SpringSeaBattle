package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.repository.domain.Ships;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShipsRepository extends MongoRepository<Ships, String> {

}
