package com.example.SchoolLunchReport.statistics.domain.boundary.support.calculator;

import static com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType.MONTHLY;

import com.example.SchoolLunchReport.statistics.controller.dto.request.PeriodSpecDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import org.springframework.stereotype.Component;

@Component
public class MonthlyBoundaryCalculator implements BoundaryCalculator {

    TemporalAdjuster monthAdjuster = TemporalAdjusters.firstDayOfMonth();

    @Override
    public PeriodType supportedType() {
        return MONTHLY;
    }

    @Override
    public Boundary createBoundary(LocalDate conditionDate) {
        LocalDate startDate = conditionDate.with(monthAdjuster);
        LocalDate endDate = LocalDate.of(startDate.getYear(), startDate.getMonth().getValue(),
            startDate.lengthOfMonth());

        return Boundary.builder()
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

    @Override
    public LocalDate getRankRegisterDate(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    @Override
    public LocalDate getStartOfPreviousPeriod(LocalDate date) {
        return date.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    }

    @Override
    public Boundary getBoundary(PeriodSpecDto periodSpecDto) {
        Integer year = periodSpecDto.year();
        int month = periodSpecDto.month().getValue();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());
        return new Boundary(startDate, endDate);
    }
}
