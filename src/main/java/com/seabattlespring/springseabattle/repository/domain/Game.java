package com.seabattlespring.springseabattle.repository.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "game")
public class Game {

    @Id
    private String id;

    private State state;//todo
    private FightField fightField1;
    private FightField fightField2;
    private String user1;
    private String user2;
    private String winner;

    public Game() {
        this.state = State.ARRANGEMENT;
        this.fightField1 = new FightField(FightField.Owner.PLAYER1);
        this.fightField2 = new FightField(FightField.Owner.PLAYER2);
    }
}
