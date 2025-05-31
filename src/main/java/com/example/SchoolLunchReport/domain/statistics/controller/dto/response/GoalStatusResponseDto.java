package com.example.SchoolLunchReport.domain.statistics.controller.dto.response;

import java.util.List;

public record GoalStatusResponseDto(
    int totalGoals,
    int achievedGoals,
    List<FoodGoalStatusDto> foodGoals
) {

    public static GoalStatusResponseDto of() {
        return new GoalStatusResponseDto(
            10,
            5,
            List.of(
                new FoodGoalStatusDto(
                    101L,
                    "김치찌개",
                    "MAIN_DISH",
                    4.5,
                    4.3,
                    2,
                    1
                ),
                new FoodGoalStatusDto(
                    102L,
                    "된장국",
                    "SOUP",
                    4.0,
                    4.1,
                    3,
                    3
                ),
                new FoodGoalStatusDto(
                    103L,
                    "비빔밥",
                    "RICE",
                    4.7,
                    4.9,
                    1,
                    2
                ),
                new FoodGoalStatusDto(
                    104L,
                    "닭갈비",
                    "MAIN_DISH",
                    4.2,
                    4.0,
                    2,
                    1
                ),
                new FoodGoalStatusDto(
                    105L,
                    "식혜",
                    "DESSERT",
                    4.3,
                    4.3,
                    1,
                    1
                )
            )
        );
    }
}