package com.example.backend.service;

import com.example.backend.entity.Stock;
import com.example.backend.repository.StockRepository;
import com.example.backend.websocket.StockWebSocketHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.example.backend.dto.PopularDTO;
import com.example.backend.dto.ResponseOutputDTO;
import com.example.backend.entity.Popular;
import com.example.backend.repository.PopularRepository;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class KafkaConsumerService {

    private final ListOperations<String, String> listOperations;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final StockWebSocketHandler webSocketHandler;

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private PopularRepository popularRepository; // JPA 또는 JDBC Repository

    @Autowired
    public KafkaConsumerService(RedisTemplate<String, String> redisTemplate, StockWebSocketHandler webSocketHandler) {
        this.listOperations = redisTemplate.opsForList();
        this.objectMapper = new ObjectMapper();
        this.redisTemplate = redisTemplate;
        this.webSocketHandler = webSocketHandler;
    }

    @KafkaListener(topicPattern = "realtime-data-.*", groupId = "volume-rank-consumer-group")
    public void consume(String message) {
        try {
            // Kafka 메시지 JSON 변환
            Map<String, String> stockData = objectMapper.readValue(message, new TypeReference<Map<String, String>>() {});
            //log.info("Kafka 메시지 수신: {}", stockData);

            // 데이터 유효성 검사 및 누락값 보완
            validateAndFillData(stockData);

            String stockId = stockData.get("stockId");
            String redisKey = "stock:" + stockId;

            // JSON 리스트 구조로 저장 (LIFO 방식)
            String jsonData = objectMapper.writeValueAsString(stockData);

            // 중복 데이터 확인
            if (isDuplicateData(redisKey, stockData)) {
                //log.info("중복 데이터 무시: {}", stockData);
                return;
            }

            // Redis에 최신 5개의 데이터 저장
            listOperations.leftPush(redisKey, jsonData);
            listOperations.trim(redisKey, 0, 4); // 리스트 크기를 5개로 제한

            //log.info("Redis에 최신 5개 데이터 저장 완료: {}", redisKey);

            // TTL 설정 (예: 1시간)
            redisTemplate.expire(redisKey, 24, java.util.concurrent.TimeUnit.HOURS);

            // WebSocket으로 실시간 데이터 전송
            webSocketHandler.broadcastMessage(jsonData);

        } catch (Exception e) {
            log.error("Kafka 메시지 처리 중 오류: ", e);
        }
    }

    /**
     * 데이터 유효성 검사 및 누락값 보완
     */
    private void validateAndFillData(Map<String, String> stockData) {
        if (!stockData.containsKey("currentPrice")) stockData.put("currentPrice", "0");
        if (!stockData.containsKey("fluctuationPrice")) stockData.put("fluctuationPrice", "0");
        if (!stockData.containsKey("fluctuationRate")) stockData.put("fluctuationRate", "0.00");
        if (!stockData.containsKey("fluctuationSign")) stockData.put("fluctuationSign", "0");
        if (!stockData.containsKey("transactionVolume")) stockData.put("transactionVolume", "0");
        if (!stockData.containsKey("tradingTime")) stockData.put("tradingTime", "000000");
    }

    /**
     * 중복 데이터 확인
     */
    private boolean isDuplicateData(String redisKey, Map<String, String> stockData) {
        String latestData = listOperations.index(redisKey, 0);
        if (latestData == null) return false;

        try {
            Map<String, String> latestDataMap = objectMapper.readValue(latestData, new TypeReference<Map<String, String>>() {});
            return stockData.equals(latestDataMap);
        } catch (Exception e) {
            log.error("중복 데이터 확인 오류: ", e);
            return false;
        }
    }

    public Popular getPopularByRanking(Integer dataRank) {
        return popularRepository.findByRanking(dataRank)
                .orElseThrow(() -> new RuntimeException("Popular not found for ranking: " + dataRank));
    }

    @Transactional
    public void updateStockByRanking(String mkscShrnIscd, Integer dataRank, String stockName, String acmlVol) {
        int updatedRows = popularRepository.updateStockByRanking(mkscShrnIscd, dataRank, stockName, Integer.valueOf(acmlVol));
        if (updatedRows == 0) {
            throw new RuntimeException("Failed to update stockId for ranking: " + dataRank);
        }
    }

    @Transactional
    public void saveStockByRanking(String mkscShrnIscd, Integer dataRank, String stockName, Integer acmlVol) {
        Popular popular = new Popular(dataRank, mkscShrnIscd, stockName, acmlVol);

        popularRepository.save(popular);
    }

    @Transactional
    public void saveStock(ResponseOutputDTO dto) {
        Stock stock = new Stock(dto.getHtsKorIsnm(), dto.getMkscShrnIscd());
        try {
            stockRepository.save(stock);
        } catch (DataIntegrityViolationException e) {
            // 중복인 경우 로그만 남기고 무시
            log.warn("Duplicate stock entry ignored: {}", stock.getStockId());
        }
    }

    @KafkaListener(topics = "volume-rank-topic", groupId = "volume-rank-consumer-group")
    @Transactional
    public void consumeMessage(String message) {
        try {
            // Deserialize JSON to DTO
            ResponseOutputDTO dto = objectMapper.readValue(message, ResponseOutputDTO.class);
            // Save to MySQL
            Integer dataRank = dto.getDataRank();
            String mkscShrnIscd = dto.getMkscShrnIscd();
            String stockName = dto.getHtsKorIsnm();
            String acmlVol = dto.getAcmlVol();

            //popular repository
            Optional<Popular> popular = popularRepository.findByRanking(dataRank);
            //log.info("popular: "+popular);
            if (popular.isPresent()) {
                //데이터가 존재하는 경우 업데이트
                PopularDTO popularDto = new PopularDTO(popular.get());

                if (!popularDto.getStockId().equals(mkscShrnIscd)) {
                    updateStockByRanking(mkscShrnIscd, dataRank, stockName, acmlVol);
                }
            } else {
                //데이터가 존재하지 않는 경우 저장
                log.info("[ERROR] Popular not found for ranking: {}", dataRank);
                saveStockByRanking(mkscShrnIscd, dataRank, stockName, Integer.valueOf(acmlVol));
            }

            //stock repository
            Optional<Stock> stock = stockRepository.findByStockId(mkscShrnIscd);
            if (stock.isEmpty()) {
                saveStock(dto);
            }

            //System.out.println("Saved to MySQL. Time: " + LocalTime.now());
        } catch (Exception e) {
            System.err.println("[ERROR] Error processing message: " + e.getMessage());
        }
    }
}