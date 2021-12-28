package com.seabattlespring.springseabattle.service;

import com.seabattlespring.springseabattle.repository.StatRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatServiceUnitTest {

    private static final String name = "username";
    private static final long value = 1;
    private static Map<String, Long> expectedStat;

    @InjectMocks
    private StatService statService;
    @Mock
    private StatRepository statRepository;

    @BeforeAll
    public static void createExpectedStat() {
        expectedStat = new LinkedHashMap<>();
        expectedStat.put(name, value);
    }

    @Test
    void getWinStat_WhenDBShouldData() {

        when(statRepository.getRangeByScore("win", 0, 1000000)).thenReturn(Set.of(name));
        when(statRepository.getScoreByValue("win", name)).thenReturn(1.0);

        Map<String, Long> actualStat = statService.getStat("win");

        assertEquals(expectedStat, actualStat);
    }

    @Test
    void getLoseStat_WhenDBShouldData() {

        when(statRepository.getRangeByScore("lose", 0, 1000000)).thenReturn(Set.of(name));
        when(statRepository.getScoreByValue("lose", name)).thenReturn(1.0);

        Map<String, Long> actualStat = statService.getStat("lose");

        assertEquals(expectedStat, actualStat);
    }

    @Test
    void getGameStat_WhenDBShouldData() {

        when(statRepository.getRangeByScore("game", 0, 1000000)).thenReturn(Set.of(name));
        when(statRepository.getScoreByValue("game", name)).thenReturn(1.0);

        Map<String, Long> actualStat = statService.getStat("game");

        assertEquals(expectedStat, actualStat);
    }
}
