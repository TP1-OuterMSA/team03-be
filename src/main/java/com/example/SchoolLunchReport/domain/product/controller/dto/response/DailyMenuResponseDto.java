package com.example.SchoolLunchReport.domain.product.controller.dto.response;

import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import java.util.List;

public record DailyMenuResponseDto(
    Long menuId,
    MealType mealType,
    String rice,
    String soup,
    String mainDish,
    String sideDish,
    String dessert,
    List<String> allergies,
    List<String> hashtags,
    Integer score // 채점되지 않았으면 null
) {

}