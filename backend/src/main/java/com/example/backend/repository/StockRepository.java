package com.example.backend.repository;

import com.example.backend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    // 특정 주식 ID로 조회
    Optional<Stock> findByStockId(String stockId);

    // 특정 주식 이름으로 조회
    List<Stock> findByStockName(String stockName);

    List<Stock> findByStockNameContaining(String stockName);
}