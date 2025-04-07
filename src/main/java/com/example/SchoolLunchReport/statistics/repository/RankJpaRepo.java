package com.example.SchoolLunchReport.statistics.repository;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RankJpaRepo extends JpaRepository<FoodRank, Long> {


    List<FoodRank> findTop5ByPeriodTypeAndStartPeriodOrderByRankingAsc(PeriodType periodType,
        LocalDate conditionDate);

    List<FoodRank> findTop5ByPeriodTypeAndStartPeriodOrderByRankingDesc(PeriodType periodType,
        LocalDate conditionDate);

    FoodRank findByFoodAndPeriodTypeAndStartPeriod(Food food, PeriodType periodType,
        LocalDate lastWeek);

    @Query("SELECT f FROM FoodRank f " +
        "WHERE f.periodType = :periodType AND f.startPeriod = :startPeriod " +
        "ORDER BY ABS(f.ranking - COALESCE(f.previousRanking, 0)) DESC")
    List<FoodRank> findTop5ByRankGapDesc(
        @Param("periodType") PeriodType periodType,
        @Param("startPeriod") LocalDate startPeriod,
        Pageable pageable
    );
}
