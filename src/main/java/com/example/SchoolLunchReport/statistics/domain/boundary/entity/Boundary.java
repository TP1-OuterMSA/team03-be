package com.example.SchoolLunchReport.statistics.domain.boundary.entity;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record Boundary(
    LocalDate startDate,
    LocalDate endDate
) {

}
