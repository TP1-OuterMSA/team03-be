package com.example.SchoolLunchReport.domain.statistics.controller.dto.response;

public record FoodGoalStatusDto(
    Long foodId,
    String foodName,
    String category,
    Double targetScore,
    Double currentScore,
    Integer targetFrequency,
    Integer currentFrequency
) {

}
