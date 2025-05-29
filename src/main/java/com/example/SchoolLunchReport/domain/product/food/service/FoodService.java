package com.example.SchoolLunchReport.domain.product.food.service;

import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.repository.dto.FoodFrequencyDto;
import com.example.SchoolLunchReport.domain.product.food.support.FoodReader;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

    final FoodReader foodReader;

    public List<FoodFrequencyDto> getFoodFrequencyByCategory(
        Boundary boundary) {
        return foodReader.getFoodFrequencyInBoundary(boundary).stream()
            .sorted(Comparator.comparing(FoodFrequencyDto::category))
            .collect(Collectors.toList());
    }

    public List<Food> getFoodList() {
        return foodReader.getFoodAll();
    }

    public Food getFoodByName(String foodName) {
        return foodReader.findByFoodName(foodName);
    }
}
