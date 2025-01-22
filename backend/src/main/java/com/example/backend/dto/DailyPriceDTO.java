package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyPriceDTO {
    @JsonProperty("stck_id")
    private String stockId;

    @JsonProperty("stck_bsop_date")
    private Integer date;  // 거래 날짜

    @JsonProperty("stck_hgpr")
    private Integer high;  // 고가

    @JsonProperty("stck_lwpr")
    private Integer low;   // 저가

    @JsonProperty("stck_clpr")
    private Integer close; // 종가

    @JsonProperty("stck_oprc")
    private Integer open;  // 시가

    @JsonProperty("prdy_ctrt")
    private Float changeRate; // 등락율

    @JsonProperty("acml_vol")
    private Integer volume; // 일거래량
}