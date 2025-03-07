package com.example.backend.service;

import com.example.backend.dto.PopularDTO;
import com.example.backend.dto.ResponseOutputDTO;
import com.example.backend.entity.Popular;
import com.example.backend.entity.Stock;
import com.example.backend.repository.PopularRepository;
import com.example.backend.repository.StockRepository;
import com.example.backend.websocket.StockWebSocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class DLTConsumerService {
    @Autowired
    private SlackNotificationService slackNotificationService;

    //volume-rank-topic-dlt
    @KafkaListener(topics = "volume-rank-topic-dlt", groupId = "my-consumer-group")
    public void retryConsumeRDS(String message) throws JsonProcessingException {
        log.info("RetryConsumeRDS message: {}", message);
        slackNotificationService.sendMessageToSlack(message);
    }

}
