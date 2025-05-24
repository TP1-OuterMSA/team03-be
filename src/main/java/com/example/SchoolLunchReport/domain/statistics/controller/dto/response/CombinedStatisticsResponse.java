package com.example.SchoolLunchReport.domain.statistics.controller.dto.response;

public record CombinedStatisticsResponse(
    StatisticsResponse weekly,
    StatisticsResponse monthly
) {

}
