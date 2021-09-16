package com.seabattlespring.springseabattle.repository.domain;

import com.seabattlespring.springseabattle.dto.Cell;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "game")
public class FightField {

    @Id
    private String id;

    private Cell[][] cells;
    List<Ships> ships;
}
