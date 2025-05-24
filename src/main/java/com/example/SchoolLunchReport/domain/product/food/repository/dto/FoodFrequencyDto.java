package com.example.SchoolLunchReport.domain.product.food.repository.dto;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.product.food.domain.type.SubCategory;

public record FoodFrequencyDto(
    Category category,
    SubCategory subCategory,
    Long count
) {

}
