package com.seabattlespring.springseabattle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NumberOfCoordinatesException extends Exception{

    public NumberOfCoordinatesException(String message) {
        super(message);
    }

    public NumberOfCoordinatesException(String message, Throwable cause) {
        super(message, cause);
    }
}
