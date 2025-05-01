package com.example.SchoolLunchReport.statistics.controller.dto.response;

import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import java.util.List;
import lombok.Builder;

@Builder
public record StatisticsResponse(
    PeriodType periodType,
    List<ScoreCount> scores
) {

    public record ScoreCount(
        double score,
        long count
    ) {

    }
}
