package com.example.SchoolLunchReport.product.food.support;

import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.product.food.repository.dto.FoodFrequencyDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodReader {

    final FoodJpaRepository foodJpaRepository;

    public List<FoodFrequencyDto> getFoodFrequencyInBoundary(Boundary boundary) {
        return foodJpaRepository.countByCategoryBetween(
            boundary.startDate(), boundary.endDate());
    }
}
