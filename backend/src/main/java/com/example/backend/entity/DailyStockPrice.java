package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "DAILY_STOCK_TB")
public class DailyStockPrice {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "daily_id")
    private int dailyId;

    @NotNull
    @Size(max=6)
    @Column(name = "stock_id", nullable = false)
    private String stockId;

    @NotNull
    @Max(99999999)
    @Column(name = "date")
    private Integer date;

    @NotNull
    @Max(value=31)
    @Column(name = "fluctuation_rate_daily")
    private Float fluctuationRateDaily;

    @NotNull
    @Column(name = "cntg_vol")
    private Integer cntgVol;

    @NotNull
    @Column(name = "opening_price")
    private Integer openingPrice;
    
    @NotNull
    @Column(name = "closing_price")
    private Integer closingPrice;

    @NotNull
    @Column(name = "high_price")
    private Integer highPrice;

    @NotNull
    @Column(name = "low_price")
    private Integer lowPrice;


    // Getters and Setters
}