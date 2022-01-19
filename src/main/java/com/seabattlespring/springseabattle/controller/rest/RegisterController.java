package com.seabattlespring.springseabattle.controller.rest;

import com.seabattlespring.springseabattle.dto.IdLocationDto;
import com.seabattlespring.springseabattle.dto.UserDto;
import com.seabattlespring.springseabattle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class RegisterController {

    private final UserService userService;

    @PostMapping(value = "user", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdLocationDto> createUser(@Valid @RequestBody UserDto userDto) {
        IdLocationDto idLocationDto = null;

        try {
            idLocationDto = userService.createUser(userDto);
            log.info("locator " + idLocationDto);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }

        if (idLocationDto == null || idLocationDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        log.info("A new user has appeared in the system");
        return ResponseEntity.status(HttpStatus.CREATED).body(idLocationDto);
    }
}
