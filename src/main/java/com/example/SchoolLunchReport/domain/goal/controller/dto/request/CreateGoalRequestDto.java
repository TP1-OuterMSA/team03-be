package com.example.SchoolLunchReport.domain.goal.controller.dto.request;

import com.example.SchoolLunchReport.domain.goal.domain.Goal;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;

public record CreateGoalRequestDto(
    Long foodId,
    Double targetScore,
    Integer targetFrequency
) {

    public Goal toEntity(Food food) {
        return new Goal(
            food,
            targetScore,
            targetFrequency
        );
    }
}
