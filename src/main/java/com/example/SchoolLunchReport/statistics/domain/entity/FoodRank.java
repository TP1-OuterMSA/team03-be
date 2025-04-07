package com.example.SchoolLunchReport.statistics.domain.entity;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FoodRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @ManyToOne
    Food food;
    LocalDate startPeriod;
    PeriodType periodType;
    Integer ranking;
    Double score;
    Integer previousRanking;

    @Builder
    public FoodRank(Food food, LocalDate startPeriod, PeriodType periodType, Integer ranking,
        Double score,
        Integer previousRanking) {
        this.food = food;
        this.startPeriod = startPeriod;
        this.periodType = periodType;
        this.ranking = ranking;
        this.score = score;
        this.previousRanking = previousRanking;
    }

    public Integer getRankGap() {
        return ranking - previousRanking;
    }
}
