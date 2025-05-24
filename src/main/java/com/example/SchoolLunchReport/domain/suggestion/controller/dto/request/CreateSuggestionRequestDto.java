package com.example.SchoolLunchReport.domain.suggestion.controller.dto.request;

import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;

public record CreateSuggestionRequestDto(
    String title,
    String nickName,
    Category category,
    String content,
    Long foodId
) {

    public Suggestion toEntity(Food food) {
        return Suggestion
            .builder()
            .title(title)
            .nickName(nickName)
            .category(category)
            .content(content)
            .food(food)
            .build();
    }
}
