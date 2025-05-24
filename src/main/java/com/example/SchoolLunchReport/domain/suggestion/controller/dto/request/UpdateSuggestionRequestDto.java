package com.example.SchoolLunchReport.domain.suggestion.controller.dto.request;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;

public record UpdateSuggestionRequestDto(
    String title,
    String nickName,
    Category category,
    String content,
    Long foodId
) {

}
