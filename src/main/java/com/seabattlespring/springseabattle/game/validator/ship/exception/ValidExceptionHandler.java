package com.seabattlespring.springseabattle.game.validator.ship.exception;

import com.seabattlespring.springseabattle.game.validator.shot.exception.ShotException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ValidExceptionHandler{

    @ExceptionHandler(value = {NumberOfCoordinatesException.class})
    public ResponseEntity<Object> handleNumberOfCoordinatesException(NumberOfCoordinatesException e) {
        ValidException validException = createExceptionObject(e);

        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {OneStraightLineException.class})
    public ResponseEntity<Object> handleOneStraightLineException(OneStraightLineException e) {
        ValidException validException = createExceptionObject(e);

        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NearbyCoordinatesException.class})
    public ResponseEntity<Object> handleNearbyCoordinatesException(NearbyCoordinatesException e) {
        ValidException validException = createExceptionObject(e);

        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NumberOfValidShipException.class})
    public ResponseEntity<Object> handleNumberOfValidShipException(NumberOfValidShipException e) {
        ValidException validException = createExceptionObject(e);

        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CellEmptyException.class})
    public ResponseEntity<Object> handleCellEmptyException(CellEmptyException e) {
        ValidException validException = createExceptionObject(e);

        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ShotException.class})
    public ResponseEntity<Object> handleShotException(ShotException e) {
        ValidException validException = createExceptionObject(e);

        return new ResponseEntity<>(validException, HttpStatus.BAD_REQUEST);
    }

    private ValidException createExceptionObject(Exception e) {
        return new ValidException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
    }
}
