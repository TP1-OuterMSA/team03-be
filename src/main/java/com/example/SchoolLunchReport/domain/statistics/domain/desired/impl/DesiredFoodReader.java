package com.example.SchoolLunchReport.domain.statistics.domain.desired.impl;

import com.example.SchoolLunchReport.domain.statistics.domain.desired.entity.FoodNameCountDto;
import com.example.SchoolLunchReport.domain.statistics.domain.desired.repo.DesiredFoodJpaRepo;
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
        List<Object[]> objectList = desiredFoodJpaRepo.findFoodNamesTopN(startDate, endDate, n);
        return objectList
            .stream()
            .map(obj -> new FoodNameCountDto(
                (String) obj[0],
                (Long) obj[1]
            ))
            .toList();
    }
}
