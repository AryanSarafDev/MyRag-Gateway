package com.myrag.gateway.Service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
@Service
public class RateLimiterService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${app.rate-limit.requests-per-minute}")
    private int requestsPerMinute;

    public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public boolean isAllowed(String userId) {
        
        String key = "rate:" + userId;
        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(1));
        }
        return count <= requestsPerMinute;
    }

    public long getRequestCount(String userId) {
        String key = "rate:" + userId;
        String count = redisTemplate.opsForValue().get(key);
        return count != null ? Long.parseLong(count) : 0;
    }
}
