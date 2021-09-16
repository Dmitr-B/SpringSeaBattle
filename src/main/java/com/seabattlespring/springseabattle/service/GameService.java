package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.repository.GameRepository;
import com.seabattlespring.springseabattle.repository.domain.Game;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class GameService {

    private final GameRepository gameRepository;

    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public Game getGameById(String id) {
        return gameRepository.findGameById(id);
    }
}
