package com.example.backend.repository;

import com.example.backend.entity.DailyStockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyStockPriceRepository extends JpaRepository<DailyStockPrice, Integer> {
    DailyStockPrice findByStockIdAndDate(String stockId, Integer date);

    List<DailyStockPrice> findByStockId(String stockCode);
}