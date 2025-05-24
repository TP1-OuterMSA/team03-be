package com.example.SchoolLunchReport.domain.suggestion.controller.dto.response;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SuggestionListItemResponseDto(
    Long id,
    String title,
    String nickName,
    Category category,
    @JsonFormat(pattern = "YYYY-MM-DD")
    LocalDate createAt
) {

    public static SuggestionListItemResponseDto from(Suggestion suggestion) {
        return SuggestionListItemResponseDto.builder()
            .id(suggestion.getId())
            .title(suggestion.getTitle())
            .nickName(suggestion.getNickName())
            .category(suggestion.getCategory())
            .createAt(suggestion.getCreatedAt())
            .build();
    }
}
