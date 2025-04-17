package com.example.SchoolLunchReport.statistics.domain.desired.impl;

import com.example.SchoolLunchReport.statistics.domain.desired.entity.FoodNameCountDto;
import com.example.SchoolLunchReport.statistics.domain.desired.repo.DesiredFoodJpaRepo;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.DesiredFood;
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
