package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class RedisController {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisController(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/redis-data/{stockId}")
    public List<String> getRedisDataByStockId(@PathVariable String stockId) {
        String redisKey = "stock:" + stockId;
        return redisTemplate.opsForList().range(redisKey, 0, -1); // 최신 1개 데이터

    }
}