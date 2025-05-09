package com.example.SchoolLunchReport.product.food.repository.dto;

import com.example.SchoolLunchReport.product.food.domain.type.Category;
import com.example.SchoolLunchReport.product.food.domain.type.SubCategory;

public record FoodFrequencyDto(
    Category category,
    SubCategory subCategory,
    Long count
) {

}
