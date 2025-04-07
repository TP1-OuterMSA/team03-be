package com.example.SchoolLunchReport.statistics.controller.dto.response;

import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record StatisticsResponse(
    PeriodType periodType,
    List<ScoreCount> scores
) {

    public record ScoreCount(
        int score,
        long count
    ) {

    }
}
