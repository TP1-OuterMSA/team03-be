package com.example.SchoolLunchReport.statistics.controller.dto.request;


import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.Semester;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Month;

public record DesiredFoodRequestDto(
    @NotNull
    @Schema(description = "타입", example = "MONTHLY, SEMESTER")
    PeriodType periodType,
    @NotNull
    @Schema(description = "년도", example = "2025")
    Integer year,
    @Schema(description = "월 (1~12)", example = "APRIL")
    Month month,
    @Min(1) @Max(2)
    @Schema(description = "학기", example = "FIRST")
    Semester semester
) {

    @AssertTrue(message = "MONTHLY일 땐 month(1~12), SEMESTER일 땐 semester(1~2)가 필요합니다")
    public boolean isValidForPeriod() {
        if (periodType == PeriodType.MONTHLY) {
            return month != null;
        } else {
            return semester != null;
        }
    }
}

