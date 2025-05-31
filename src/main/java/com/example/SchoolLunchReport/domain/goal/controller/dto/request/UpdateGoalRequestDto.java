package com.example.SchoolLunchReport.domain.goal.controller.dto.request;

public record UpdateGoalRequestDto(
    Double targetScore,
    Integer targetFrequency
) {

}
