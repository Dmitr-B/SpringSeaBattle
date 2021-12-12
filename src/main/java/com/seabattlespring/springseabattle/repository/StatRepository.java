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

    public void save(String key, String value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Boolean saveIfNotPresent(String key, String value) {
        return redisTemplate.opsForZSet().addIfAbsent(key, value, 1);
    }

    public void incrementScore(String key, String value) {
        redisTemplate.opsForZSet().incrementScore(key, value, 1);
    }

    public void delete(String key, String value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    public Double getScoreByValue(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

}
