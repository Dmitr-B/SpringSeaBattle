package com.seabattlespring.springseabattle.controller.rest;

import com.seabattlespring.springseabattle.service.StatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/game/stat")
public class StatController {

    private final StatService statService;

    @GetMapping("/won")
    public ResponseEntity<Map<String, Long>> getWonStat() {
        Map<String, Long> stat = statService.getStat("win");

        if (stat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(stat);
    }

    @GetMapping("/lose")
    public ResponseEntity<Map<String, Long>> getLoseStat() {
        Map<String, Long> stat = statService.getStat("lose");

        if (stat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(stat);
    }

    @GetMapping("/game")
    public ResponseEntity<Map<String, Long>> getNumberOfGameStat() {
        Map<String, Long> stat = statService.getStat("game");

        if (stat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(stat);
    }
}
