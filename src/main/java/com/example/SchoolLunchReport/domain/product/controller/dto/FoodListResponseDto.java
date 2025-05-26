package com.example.SchoolLunchReport.domain.product.controller.dto;

import com.example.SchoolLunchReport.domain.product.food.domain.type.SubCategory;

public record FoodListResponseDto(
    SubCategory subCategory,
    Long foodId,
    String foodName
) {

}
