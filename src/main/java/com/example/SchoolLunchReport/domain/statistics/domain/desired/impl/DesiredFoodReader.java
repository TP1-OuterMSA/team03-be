package com.example.SchoolLunchReport.domain.statistics.domain.desired.impl;

import com.example.SchoolLunchReport.domain.statistics.domain.desired.repo.DesiredFoodJpaRepo;
import com.example.SchoolLunchReport.domain.statistics.domain.desired.entity.FoodNameCountDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DesiredFoodReader {

    final DesiredFoodJpaRepo desiredFoodJpaRepo;

    public List<FoodNameCountDto> getDesiredFoodInBoundaryTopN(
        LocalDate startDate,
        LocalDate endDate,
        int n
    ) {
        return desiredFoodJpaRepo.findFoodNamesTopN(startDate, endDate, n);
    }
}
