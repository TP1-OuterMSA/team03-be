package com.example.SchoolLunchReport.statistics.domain.rank.repo;

import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RankJpaRepo extends JpaRepository<FoodRank, Long> {

    List<FoodRank> findTop5ByPeriodTypeAndRegisterDateOrderByRankingAsc(PeriodType periodType,
        LocalDate conditionDate);

    List<FoodRank> findTop5ByPeriodTypeAndRegisterDateOrderByRankingDesc(PeriodType periodType,
        LocalDate conditionDate);

    @Query("SELECT f FROM FoodRank f " +
        "WHERE f.periodType = :periodType AND f.registerDate = :registerDate " +
        "AND f.previousRanking IS NOT NULL " +
        "ORDER BY (f.previousRanking - f.ranking) DESC")
    List<FoodRank> findTop5ByRankGapDesc(
        @Param("periodType") PeriodType periodType,
        @Param("registerDate") LocalDate registerDate,
        Pageable pageable
    );

    @Query("SELECT f FROM FoodRank f " +
        "WHERE f.periodType = :periodType " +
        "AND f.registerDate = :registerDate " +
        "AND (f.previousRanking - f.ranking) >= 5 " +
        "ORDER BY (f.previousRanking - f.ranking) DESC")
    List<FoodRank> findTop5ByRankGapDescFiltered(
        @Param("periodType") PeriodType periodType,
        @Param("registerDate") LocalDate registerDate,
        Pageable pageable
    );

    List<FoodRank> findByPeriodTypeAndRegisterDate(PeriodType periodType, LocalDate startPeriod);
}
