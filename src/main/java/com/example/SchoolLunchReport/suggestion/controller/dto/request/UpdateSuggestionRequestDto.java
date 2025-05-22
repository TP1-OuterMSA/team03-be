package com.example.SchoolLunchReport.suggestion.controller.dto.request;

import com.example.SchoolLunchReport.product.food.domain.type.Category;

public record UpdateSuggestionRequestDto(
    String title,
    String nickName,
    Category category,
    String content,
    Long foodId
) {

}
