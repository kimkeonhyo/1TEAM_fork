package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "POPULAR_TB")
public class Popular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rankingId;

    @Column(name = "stock_id", nullable = false)
    private String stockId;

    @Column(name = "ranking")
    private Integer ranking;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "acmlvol")
    private Integer acmlvol;

    // Getters and Setters
    public Popular(Integer ranking, String stockId, String stockName, Integer acmlvol) {
        this.ranking = ranking;
        this.stockId = stockId;
        this.stockName = stockName;
        this.acmlvol = acmlvol;
    }
}