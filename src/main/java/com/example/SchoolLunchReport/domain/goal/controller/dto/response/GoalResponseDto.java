package com.example.SchoolLunchReport.domain.goal.controller.dto.response;

import com.example.SchoolLunchReport.domain.goal.domain.Goal;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record GoalResponseDto(
    Long goalId,
    Long foodId,
    String foodName,
    Category category,
    Double targetScore,
    Integer targetFrequency,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createdAt,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate updatedAt

) {

    public static GoalResponseDto toEntity(Goal goal) {
        return GoalResponseDto.builder()
            .goalId(goal.getId())
            .foodId(goal.getFood().getId())
            .foodName(goal.getFood().getName())
            .category(goal.getFood().getCategory())
            .targetScore(goal.getTargetScore())
            .targetFrequency(goal.getTargetFrequency())
            .createdAt(goal.getCreatedAt())
            .updatedAt(goal.getUpdatedAt())
            .build();

    }
}
