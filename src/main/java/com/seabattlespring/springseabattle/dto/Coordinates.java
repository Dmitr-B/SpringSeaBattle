package com.seabattlespring.springseabattle.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class Coordinates {

    @Min(value = 0, message = "x must not be below 0")
    @Max(value = 9, message = "y must not be below 9")
    private int x;

    @Min(value = 0, message = "x must not be below 0")
    @Max(value = 9, message = "y must not be below 9")
    private int y;

}
