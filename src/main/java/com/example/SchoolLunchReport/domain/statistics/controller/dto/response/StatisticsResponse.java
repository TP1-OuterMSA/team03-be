package com.example.SchoolLunchReport.domain.statistics.controller.dto.response;

import com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType;
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
