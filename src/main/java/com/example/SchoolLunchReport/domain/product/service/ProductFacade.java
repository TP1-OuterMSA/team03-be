package com.example.SchoolLunchReport.domain.product.service;

import com.example.SchoolLunchReport.domain.product.controller.dto.FoodListResponseDto;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.product.food.service.FoodService;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    final FoodService foodService;

    public Map<Category, List<FoodListResponseDto>> getMenuListByCategory() {
        List<Category> categoryOrder = List.of(
            Category.RICE,
            Category.MAIN_DISH,
            Category.SIDE_DISH,
            Category.DESSERT
        );

        List<Food> foodList = foodService.getFoodList();

        Map<Category, List<FoodListResponseDto>> grouped = foodList.stream()
            .collect(Collectors.groupingBy(
                Food::getCategory,
                Collectors.mapping(
                    f -> new FoodListResponseDto(f.getSubCategory(), f.getId(), f.getName()),
                    Collectors.toList()
                )
            ));

        Map<Category, List<FoodListResponseDto>> orderedMap = new LinkedHashMap<>();
        for (Category category : categoryOrder) {
            List<FoodListResponseDto> items = grouped.getOrDefault(category,
                Collections.emptyList());
            orderedMap.put(category, items);
        }
        return orderedMap;
    }
}
