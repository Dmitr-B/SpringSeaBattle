package com.seabattlespring.springseabattle.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Data
@AllArgsConstructor
public class RedisGameRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void addAvailableGame(String key, String value, long expiration) {
        Date date = new Date();
        redisTemplate.opsForList().rightPush(key, value + "::" + (date.getTime() + expiration));
    }

    public String getAvailableGame(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

}
