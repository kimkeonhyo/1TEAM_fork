package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DailyPriceStockNameDTO {
    private String stockName;
    private String stockId;

    private Integer date;  // 거래 날짜

    private Integer high;  // 고가

    private Integer low;   // 저가

    private Integer close; // 종가

    private Integer open;  // 시가

    private Float changeRate; // 등락율

    private Integer volume; // 일거래량
}
