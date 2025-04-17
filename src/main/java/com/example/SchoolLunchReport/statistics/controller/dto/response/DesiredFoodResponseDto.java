package com.example.SchoolLunchReport.statistics.controller.dto.response;

import com.example.SchoolLunchReport.statistics.domain.desired.entity.FoodNameCountDto;
import lombok.Builder;

@Builder
public record DesiredFoodResponseDto(
    Integer rank,
    String foodName,
    Long score
) {

    public static DesiredFoodResponseDto of(FoodNameCountDto foodNameCountDto, int rank) {
        return DesiredFoodResponseDto.builder()
            .rank(rank)
            .foodName(foodNameCountDto.foodName())
            .score(foodNameCountDto.count())
            .build();
    }
}
