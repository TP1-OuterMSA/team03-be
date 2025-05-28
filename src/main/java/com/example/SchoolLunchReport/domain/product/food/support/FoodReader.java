package com.example.SchoolLunchReport.domain.product.food.support;

import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.product.food.domain.type.SubCategory;
import com.example.SchoolLunchReport.domain.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.repository.dto.FoodFrequencyDto;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodReader {

    final FoodJpaRepository foodJpaRepository;

    public List<FoodFrequencyDto> getFoodFrequencyInBoundary(Boundary boundary) {
        List<Object[]> objects = foodJpaRepository.countFoodByCategory(
            boundary.startDate(), boundary.endDate());
        List<FoodFrequencyDto> result = objects.stream()
            .map(obj -> new FoodFrequencyDto(
                (Category) obj[0],
                (SubCategory) obj[1],
                (Long) obj[2]
            ))
            .toList();
        return result;
    }

    public Food findByFoodName(String foodName) {
        return foodJpaRepository.findByName(foodName).orElseThrow(
            () -> new EntityNotFoundException("없는 음식 이름 입니다.")
        );
    }

    public Food getFoodById(Long foodId) {
        return foodJpaRepository.findById(foodId).orElseThrow(
            () -> new EntityNotFoundException("없는 음식 아이디입니다")
        );
    }

    public List<Food> getFoodAll() {
        return new ArrayList<>(foodJpaRepository.findAll().stream()
            .collect(Collectors.toMap(
                Food::getName,
                food -> food,
                (f1, f2) -> f1
            ))
            .values());
    }
    
}
