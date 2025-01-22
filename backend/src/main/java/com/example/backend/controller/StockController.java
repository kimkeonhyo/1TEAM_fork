package com.example.backend.controller;

import com.example.backend.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/search/{stockName}")
    public List<String> searchStock(@PathVariable String stockName) throws Exception {
        try {
            List<String> lst = stockService.findWith(stockName);
            log.info("[LOG] /api/search 성공");
            return lst;
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] /api/search 오류 발생 "+e);
        }
    }

}
