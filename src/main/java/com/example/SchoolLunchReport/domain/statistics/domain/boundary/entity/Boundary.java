package com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record Boundary(
    //TODO 패키지명 변경
    LocalDate startDate,
    LocalDate endDate
) {

}
