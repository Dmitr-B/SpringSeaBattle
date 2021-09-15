package com.seabattlespring.springseabattle.repository.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seabattlespring.springseabattle.dto.Cell;
import com.seabattlespring.springseabattle.dto.Coordinates;
import com.seabattlespring.springseabattle.dto.Ship;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
//@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Document(collection = "ships")
public class Ships {

    public static  final  String SEQUENCE_NAME = "ships-sequence";

    @Id
    private String id;

    @JsonProperty("type")
    private ShipType shipType;

    @JsonProperty("coordinates")
    private List<Coordinates> coordinates;
    //private Coordinates coordinates;


//    @JsonCreator
//    public Ships(ShipType shipType, List<Coordinates> coordinates) {
//        this.shipType = shipType;
//        this.coordinates = coordinates;
//    }
}
