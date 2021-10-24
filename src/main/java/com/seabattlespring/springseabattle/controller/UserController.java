package com.seabattlespring.springseabattle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    @GetMapping("auth/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("auth/success")
    public String getSuccessPage() {
        return "success";
    }
}
