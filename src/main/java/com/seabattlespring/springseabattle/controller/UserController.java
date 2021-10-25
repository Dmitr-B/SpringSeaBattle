package com.seabattlespring.springseabattle.controller;

import com.seabattlespring.springseabattle.dto.IdLocationDto;
import com.seabattlespring.springseabattle.dto.UserDto;
import com.seabattlespring.springseabattle.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URISyntaxException;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @GetMapping("auth/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("auth/success")
    public String getSuccessPage() {
        return "success";
    }

    @GetMapping("user")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("user")
    public ResponseEntity<IdLocationDto> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        IdLocationDto idLocationDto = null;

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            idLocationDto = userService.createUser(userDto);
            log.info("locator " + idLocationDto);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (idLocationDto.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(idLocationDto);
    }

}
