package com.example.SchoolLunchReport.domain.statistics.domain.boundary.support.calculator;

import com.example.SchoolLunchReport.domain.statistics.controller.dto.request.PeriodSpecRequestDto;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import java.time.LocalDate;


public interface BoundaryCalculator {

    PeriodType supportedType();

    Boundary createBoundary(LocalDate conditionDate);

    LocalDate getRankRegisterDate(LocalDate date);

    LocalDate getStartOfPreviousPeriod(LocalDate date);

    Boundary getBoundary(PeriodSpecRequestDto periodSpecRequestDto);
}
