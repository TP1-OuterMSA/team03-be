package com.example.SchoolLunchReport.domain.statistics.controller.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record TrackingResponseDto(
    List<Double> weeklyScore,
    List<Double> monthlyScore
) {

}
