package com.example.SchoolLunchReport.domain.statistics.domain.boundary.support;

import com.example.SchoolLunchReport.domain.statistics.controller.dto.request.PeriodSpecRequestDto;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.support.calculator.BoundaryCalculator;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BoundaryMapper {

    final Map<PeriodType, BoundaryCalculator> calculatorMap;

    public BoundaryMapper(List<BoundaryCalculator> calculators) {

        this.calculatorMap = new EnumMap<>(PeriodType.class);
        for (BoundaryCalculator calc : calculators) {
            PeriodType key = calc.supportedType();
            if (calculatorMap.putIfAbsent(key, calc) != null) {
                throw new IllegalStateException("Duplicate calculator for: " + key);
            }
        }
    }

    public Boundary mapBoundary(PeriodSpecRequestDto periodSpecRequestDto) {
        PeriodType periodType = periodSpecRequestDto.periodType();
        BoundaryCalculator boundaryCalculator = calculatorMap.get(periodType);
        return boundaryCalculator.getBoundary(periodSpecRequestDto);
    }

    public Boundary mapBoundary(PeriodType periodType, LocalDate conditionDate) {
        BoundaryCalculator boundaryCalculator = calculatorMap.get(periodType);
        return boundaryCalculator.createBoundary(conditionDate);
    }

    public LocalDate getRankRegisterDate(PeriodType periodType, LocalDate date) {
        BoundaryCalculator boundaryCalculator = calculatorMap.get(periodType);
        return boundaryCalculator.getRankRegisterDate(date);
    }

    public LocalDate getStartOfPreviousPeriod(PeriodType periodType, LocalDate date) {
        BoundaryCalculator boundaryCalculator = calculatorMap.get(periodType);
        return boundaryCalculator.getStartOfPreviousPeriod(date);
    }
}
