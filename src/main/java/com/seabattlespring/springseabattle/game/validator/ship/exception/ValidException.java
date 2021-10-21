package com.seabattlespring.springseabattle.game.validator.ship.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ValidException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timeStamp;
}
