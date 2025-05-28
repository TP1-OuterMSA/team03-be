package com.example.SchoolLunchReport.domain.suggestion.controller.dto.response;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SuggestionResponseDto(
    Long id,
    String title,
    String nickName,
    Category category,
    String content,
    String foodName,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createAt
) {

    public static SuggestionResponseDto toEntity(Suggestion suggestion) {
        return SuggestionResponseDto
            .builder()
            .id(suggestion.getId())
            .title(suggestion.getTitle())
            .content(suggestion.getContent())
            .category(suggestion.getCategory())
            .nickName(suggestion.getNickName())
            .createAt(suggestion.getCreatedAt())
            .foodName(suggestion.getFood().getName())
            .build();
    }
}
