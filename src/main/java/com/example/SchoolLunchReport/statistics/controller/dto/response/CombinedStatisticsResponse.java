package com.example.SchoolLunchReport.statistics.controller.dto.response;

public record CombinedStatisticsResponse(
    StatisticsResponse weekly,
    StatisticsResponse monthly
) {

}
