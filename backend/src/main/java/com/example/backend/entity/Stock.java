package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "STOCK_TB")
@Getter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @Column(name = "stock_id", nullable = false, unique = true)
    private String stockId;

    protected Stock() {
        // JPA에서 기본 생성자 필요
    }

    public Stock(String stockId) {
        this.stockId = stockId;
    }

    public Stock(String stockName, String stockId) {
        this.stockName = stockName;
        this.stockId = stockId;
    }

    public Integer getId() {
        return id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}