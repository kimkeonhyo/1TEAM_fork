package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingDTO {

    private Integer ranking; //순위

    private String stockName; //stock name

    private String stockId; //stock id

    private Float changeRate; // 등락율

    private Integer volume; // 일거래량

    public RankingDTO(Integer ranking, String stockName, String stockId, Float changeRate, Integer volume) {
        this.ranking = ranking;
        this.stockName = stockName;
        this.stockId = stockId;
        this.changeRate = changeRate;
        this.volume = volume;
    }
}
