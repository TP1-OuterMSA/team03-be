package com.example.SchoolLunchReport.domain.product.FoodMenu.support;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FoodMenuReader {

    final FoodMenuJpaRepository foodMenuJpaRepository;
    
    public FoodMenu getByMenuAndFood(Menu menu, Food food) {
        return foodMenuJpaRepository.findByMenuAndFood(menu, food).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("없는 foodMenu 입니다. menuID: %d, foodId: %d", menu.getId(),
                    food.getId()))
        );
    }
}
