package com.example.SchoolLunchReport.statistics.controller.dto.response.response;

public record CombinedStatisticsResponse(
    StatisticsResponse weekly,
    StatisticsResponse monthly
) {

}
