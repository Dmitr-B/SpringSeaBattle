package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.repository.FightFieldRepository;
import com.seabattlespring.springseabattle.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class FightFieldService {

    private final FightFieldRepository fightFieldRepository;
    private final GameRepository gameRepository;

    
}
