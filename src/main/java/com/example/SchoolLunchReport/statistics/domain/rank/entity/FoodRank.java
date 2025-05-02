package com.example.SchoolLunchReport.statistics.domain.rank.entity;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;
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

    LocalDate registerDate;
  
    PeriodType periodType;

    Integer ranking;

    Double score;

    Integer previousRanking;

    private static final Integer NOT_CHANGE = 0;

    @Builder
    public FoodRank(Food food, LocalDate registerDate, PeriodType periodType, Integer ranking,
        Double score) {
        this.food = food;
        this.registerDate = registerDate;
        this.periodType = periodType;
        this.ranking = ranking;
        this.score = score;
    }

    public Integer getRankGap() {
        if (Objects.isNull(previousRanking)) {
            return NOT_CHANGE;
        }
        return previousRanking - ranking;
    }

    public void setPreviousRanking(Integer previousRanking) {
        this.previousRanking = previousRanking;
    }
}
