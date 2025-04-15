package com.example.SchoolLunchReport.statistics.controller.dto.response.response;

import java.util.List;
import lombok.Builder;

@Builder
public record TrackingResponseDto(
    List<Double> weeklyScore,
    List<Double> monthlyScore
) {

}
