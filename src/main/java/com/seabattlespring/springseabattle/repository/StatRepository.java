package com.seabattlespring.springseabattle.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Data
@AllArgsConstructor
public class StatRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public void saveWin(String key, String value) {
        redisTemplate.opsForZSet().add(key, value, 1);
    }

}
