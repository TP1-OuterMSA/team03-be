package com.example.SchoolLunchReport.statistics.domain.boundary.support.calculator;

import static com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType.SEMESTER;

import com.example.SchoolLunchReport.statistics.controller.dto.request.PeriodSpecRequestDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.Semester;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class SemesterBoundaryCalculator implements BoundaryCalculator {

    @Override
    public PeriodType supportedType() {
        return SEMESTER;
    }

    @Override
    public Boundary createBoundary(LocalDate conditionDate) {

        return null;
    }

    @Override
    public LocalDate getRankRegisterDate(LocalDate date) {
        return null;
    }

    @Override
    public LocalDate getStartOfPreviousPeriod(LocalDate date) {
        return null;
    }

    @Override
    public Boundary getBoundary(PeriodSpecRequestDto periodSpecRequestDto) {
        LocalDate startDate = getSemesterStartDate(periodSpecRequestDto.year(),
            periodSpecRequestDto.semester());
        LocalDate endDate = getSemesterEndDate(startDate);
        return new Boundary(startDate, endDate);
    }

    private LocalDate getSemesterEndDate(LocalDate startDate) {
        return startDate.plusMonths(3)
            .withDayOfMonth(startDate.plusMonths(3).lengthOfMonth());
    }

    private LocalDate getSemesterStartDate(Integer year, Semester semester) {
        if (semester == Semester.FIRST) {
            return LocalDate.of(year, 3, 1);
        }
        return LocalDate.of(year, 6, 1);
    }
}
