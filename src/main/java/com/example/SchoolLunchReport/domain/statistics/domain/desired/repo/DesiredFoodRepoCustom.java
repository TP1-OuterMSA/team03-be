package com.example.SchoolLunchReport.domain.statistics.domain.desired.repo;

import com.example.SchoolLunchReport.domain.statistics.domain.desired.entity.FoodNameCountDto;
import java.time.LocalDate;
import java.util.List;

public interface DesiredFoodRepoCustom {

    List<FoodNameCountDto> findFoodNamesTopN(LocalDate startDate, LocalDate endDate, int n);
}
