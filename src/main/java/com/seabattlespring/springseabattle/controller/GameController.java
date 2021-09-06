package com.seabattlespring.springseabattle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
//@RequestMapping("/gameweb")
public class GameController {

    @GetMapping("/play")
    //@ResponseBody
    public String index() {
        return "play";
    }
//
//    @GetMapping
//    public ResponseEntity<String> display() {
//        return ResponseEntity.ok().build();
//    }
}
