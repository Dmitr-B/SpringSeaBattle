package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.repository.StatRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Map<String, Long> getStat(String key) {
        Map<String, Long> stat = new LinkedHashMap<>();
        Set<String> data = statRepository.getRangeByScore(key, 0, 1000000);
        log.info("data " + data);

        for (String statData:data) {
            long winScore = Math.round(statRepository.getScoreByValue(key, statData));
            stat.put(statData, winScore);
        }

        return stat;
    }

}
