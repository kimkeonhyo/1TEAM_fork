package com.example.backend.dto;

import com.example.backend.entity.Popular;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopularDTO {
    private String stockId;
    private Integer ranking;
    private String stockName;
    private Integer acmlvol;

    public PopularDTO(Popular popular) {
        this.stockId = popular.getStockId();
        this.ranking = popular.getRanking();
        this.stockName = popular.getStockName();
        this.acmlvol = popular.getAcmlvol();
    }
}
