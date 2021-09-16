package com.seabattlespring.springseabattle.repository.domain;

import com.seabattlespring.springseabattle.dto.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "game")
public class Cell {

//    @Id
//    private String id;

    private CellState cellState;
    private Coordinates coordinates;
}
