package com.seabattlespring.springseabattle.repository;

import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.repository.domain.Cell;
import com.seabattlespring.springseabattle.repository.domain.Ships;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShipsRepository extends MongoRepository<Ships, String> {
    Ships findByCells(Cell cell);
    Ships findByCellsContains(Coordinates coordinates);
    Ships findShipsByCellsContaining(Coordinates coordinates);
}
