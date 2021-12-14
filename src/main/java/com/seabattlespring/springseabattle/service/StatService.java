package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.repository.StatRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatService {

    private final StatRepository statRepository;

//    public Map<String, Integer> getWonStat() {
//        Map<String, Integer> wonStat = new HashMap<>();
//        Set<ZSetOperations.TypedTuple<String>> wonData = statRepository.getRangeWithScore("win", 0, -1);
//
//    }

}
