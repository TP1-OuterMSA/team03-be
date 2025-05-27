package com.example.SchoolLunchReport.domain.suggestion.controller.dto.request;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;

public record CreateSuggestionRequestDto(
    String title,
    String nickName,
    Category category,
    String content,
    String foodName
) {

    public Suggestion toEntity() {
        return Suggestion
            .builder()
            .title(title)
            .nickName(nickName)
            .category(category)
            .content(content)
            .foodName(foodName)
            .build();
    }
}
