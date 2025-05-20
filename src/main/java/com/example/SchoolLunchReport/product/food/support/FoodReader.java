package com.example.SchoolLunchReport.product.food.support;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.product.food.repository.dto.FoodFrequencyDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import jakarta.persistence.EntityNotFoundException;
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

    public Food findByFoodName(String foodName) {
        return foodJpaRepository.findByName(foodName).orElseThrow(
            () -> new EntityNotFoundException("없는 음식 이름 입니다.")
        );
    }
}
