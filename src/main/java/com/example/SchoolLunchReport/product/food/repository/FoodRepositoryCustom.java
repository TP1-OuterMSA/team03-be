package com.example.SchoolLunchReport.product.food.repository;

import com.example.SchoolLunchReport.product.food.repository.dto.FoodFrequencyDto;
import java.time.LocalDate;
import java.util.List;

public interface FoodRepositoryCustom {

    List<FoodFrequencyDto> countByCategoryBetween(LocalDate startDate,
        LocalDate endDate);
}
