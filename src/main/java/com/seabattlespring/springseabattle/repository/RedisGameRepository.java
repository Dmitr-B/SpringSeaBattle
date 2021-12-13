package com.seabattlespring.springseabattle.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Data
@AllArgsConstructor
public class RedisGameRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void addWaitingGame(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }
}
