package com.example.backend.service;

import com.example.backend.entity.Stock;
import com.example.backend.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StockService {


    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public List<String> findWith(String stockName) throws Exception {
        List<String> lst = new ArrayList<>();

        List<Stock> stocks = stockRepository.findByStockNameContaining(stockName);
        for (Stock stock : stocks) {
            lst.add(stock.getStockId());
        }
        return lst;
    }
}
