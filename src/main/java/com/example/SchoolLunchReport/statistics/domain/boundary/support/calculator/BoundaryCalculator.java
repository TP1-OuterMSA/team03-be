package com.example.SchoolLunchReport.statistics.domain.boundary.support.calculator;

import com.example.SchoolLunchReport.statistics.controller.dto.request.PeriodSpecRequestDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import java.time.LocalDate;


public interface BoundaryCalculator {

    PeriodType supportedType();

    Boundary createBoundary(LocalDate conditionDate);

    LocalDate getRankRegisterDate(LocalDate date);

    LocalDate getStartOfPreviousPeriod(LocalDate date);

    Boundary getBoundary(PeriodSpecRequestDto periodSpecRequestDto);
}
