package com.example.SchoolLunchReport.statistics.rank.controller.dto;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import lombok.Builder;

@Builder
public record RankMenuResponse(
    Long id,
    String name,
    Double score,
    Integer rankChange,
    Integer rank
) {

    public static RankMenuResponse of(Food food, Double score, Integer rank) {
        return RankMenuResponse.builder()
            .id(food.getId())
            .name(food.getFoodName())
            .score(score)
            .rankChange(0)
            .rank(rank)
            .build();
    }
}
