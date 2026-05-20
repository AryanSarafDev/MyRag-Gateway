package com.myrag.gateway.Service;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private final RedisTemplate<String, String> redisTemplate;

    public CacheService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public Optional<String> getCachedResponse(String query){
        String value = redisTemplate.opsForValue().get("cache:" + query);
        return Optional.ofNullable(value);
    }
    public void cacheResponse(String query, String response){
        redisTemplate.opsForValue().set("cache:"+query,response,Duration.ofHours(1));
    }
}
