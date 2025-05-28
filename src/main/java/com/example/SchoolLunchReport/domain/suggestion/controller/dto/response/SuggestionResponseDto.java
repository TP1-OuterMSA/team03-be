package com.example.SchoolLunchReport.domain.suggestion.controller.dto.response;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Answer;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Nullable;
import lombok.Builder;

@Builder
public record SuggestionResponseDto(
    Long id,
    String title,
    String nickName,
    Category category,
    String content,
    @Nullable
    List<AnswerResponseDto> answers,
    String foodName,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createAt
) {

    public static SuggestionResponseDto toEntity(Suggestion suggestion) {
        List<Answer> answers = suggestion.getAnswers();
        List<AnswerResponseDto> list = answers.stream()
            .map(AnswerResponseDto::from)
            .toList();

        return SuggestionResponseDto.builder()
            .id(suggestion.getId())
            .title(suggestion.getTitle())
            .content(suggestion.getContent())
            .category(suggestion.getCategory())
            .nickName(suggestion.getNickName())
            .createAt(suggestion.getCreatedAt())
            .answers(list)
            .foodName(suggestion.getFood().getName())
            .build();
    }
}
