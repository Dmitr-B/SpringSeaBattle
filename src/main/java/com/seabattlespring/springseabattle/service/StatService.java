package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class StatService {

    private final StatRepository statRepository;

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
