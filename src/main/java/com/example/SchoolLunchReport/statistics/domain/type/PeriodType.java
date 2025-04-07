package com.example.SchoolLunchReport.statistics.domain.type;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import org.springframework.cglib.core.Local;

public enum PeriodType {
    WEEKLY, MONTHLY;

    public LocalDate getStartOfThisPeriod(LocalDate date) {
        return switch (this) {
            case WEEKLY -> date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            case MONTHLY -> date.with(TemporalAdjusters.firstDayOfMonth());
        };
    }

    public LocalDate getLastOfThisPeriod(LocalDate date) {
        return switch (this) {
            case WEEKLY ->
                date.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            case MONTHLY -> date.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        };
    }
}
