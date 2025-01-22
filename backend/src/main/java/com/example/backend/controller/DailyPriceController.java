package com.example.backend.controller;

import com.example.backend.dto.DailyPriceDTO;
import com.example.backend.dto.DailyPriceStockNameDTO;
import com.example.backend.service.DailyPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.service.KisTokenService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/daily-price")
@RequiredArgsConstructor
public class DailyPriceController {

    private final DailyPriceService dailyPriceService; 
    private final KisTokenService kisTokenService;

    @PostMapping("/{stockCode}")
    public ResponseEntity<List<DailyPriceDTO>> postDailyPrices(@PathVariable String stockCode) {
        try {
            String accessToken = kisTokenService.getCachedAccessToken();
            List<DailyPriceDTO> dailyPrices = dailyPriceService.postDailyPrice(stockCode, accessToken);
            dailyPriceService.saveList(dailyPrices);
            log.info("[LOG] POST /api/daily-price 성공");
            return ResponseEntity.ok(dailyPrices);
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] POST /api/daily-price 오류 발생 "+e);
        }
    }

    @GetMapping("/{stockCode}")
    public ResponseEntity<List<DailyPriceStockNameDTO>> getDailyPrices(@PathVariable String stockCode) {
        try {
            List<DailyPriceStockNameDTO> dailyPrices = dailyPriceService.getDailyPrice(stockCode);
            log.info("[LOG] GET /api/daily-price 성공");
            return ResponseEntity.ok(dailyPrices);
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] GET /api/daily-price 오류 발생"+e);
        }
    }

}