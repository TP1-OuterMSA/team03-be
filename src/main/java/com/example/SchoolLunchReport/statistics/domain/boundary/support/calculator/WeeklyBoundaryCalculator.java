package com.example.SchoolLunchReport.statistics.domain.boundary.support.calculator;

import com.example.SchoolLunchReport.statistics.controller.dto.request.PeriodSpecDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import org.springframework.stereotype.Component;

@Component
public class WeeklyBoundaryCalculator implements BoundaryCalculator {

    @Override
    public PeriodType supportedType() {
        return PeriodType.WEEKLY;
    }

    @Override
    public Boundary createBoundary(LocalDate conditionDate) {
        LocalDate startLocalDate = conditionDate.with(
            TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endLocalDate = startLocalDate.plusDays(5);
        return Boundary.builder()
            .startDate(startLocalDate)
            .endDate(endLocalDate)
            .build();
    }

    @Override
    public LocalDate getRankRegisterDate(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    @Override
    public LocalDate getStartOfPreviousPeriod(LocalDate date) {
        return date.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
    }

    @Override
    public Boundary getBoundary(PeriodSpecDto periodSpecDto) {
        return null;
    }
}