package com.example.SchoolLunchReport.domain.suggestion.controller.dto.request;

import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Answer;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;

public record CreateAnswerRequestDto(
    String managerName,
    String content
) {

    public Answer toEntity(Suggestion suggestion) {
        return Answer.builder()
            .managerName(managerName)
            .content(content)
            .suggestion(suggestion)
            .build();
    }
}
