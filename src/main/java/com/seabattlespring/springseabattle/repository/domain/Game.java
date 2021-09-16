package com.seabattlespring.springseabattle.repository.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//@NoArgsConstructor
@Document(collection = "game")
public class Game {

    @Id
    private String id;

    private State state;
    private FightField fightField1;
    private FightField fightField2;

    public Game() {
        this.state = State.ARRANGEMENT;
        this.fightField1 = new FightField(FightField.Owner.PLAYER1);
        this.fightField2 = new FightField(FightField.Owner.PLAYER2);
    }
}
