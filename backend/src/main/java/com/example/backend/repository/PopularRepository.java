package com.example.backend.repository;

import com.example.backend.entity.Popular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PopularRepository extends JpaRepository<Popular, Integer> {
    Optional<Popular> findByRanking(Integer ranking);

    // ranking 값으로 stockId 변경
    @Modifying
    @Query("UPDATE Popular p SET p.stockId = :stockId, p.stockName = :stockName, p.acmlvol = :acmlVol WHERE p.ranking = :ranking")
    int updateStockByRanking(String stockId, Integer ranking, String stockName, Integer acmlVol);

    List<Popular> findByRankingBetween(Integer lower, Integer upper);

    Popular findByStockId(String stockId);
}