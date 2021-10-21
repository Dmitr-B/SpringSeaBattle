package com.seabattlespring.springseabattle.exception;

import com.seabattlespring.springseabattle.controller.GameController;
import com.seabattlespring.springseabattle.exception.NumberOfCoordinatesException;
import com.seabattlespring.springseabattle.exception.ValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice()
public class ValidExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NumberOfCoordinatesException.class})
    public ResponseEntity<Object> handleNumberOfCoordinatesException(NumberOfCoordinatesException e) {
        ValidException validException = new ValidException(
                e.getMessage(),
                e,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }
}
