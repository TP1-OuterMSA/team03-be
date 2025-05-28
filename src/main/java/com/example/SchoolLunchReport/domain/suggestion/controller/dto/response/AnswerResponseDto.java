package com.example.SchoolLunchReport.domain.suggestion.controller.dto.response;

import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Answer;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record AnswerResponseDto(
    String managerName,
    String content,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate createDate
) {

    public static AnswerResponseDto from(Answer answer) {
        return new AnswerResponseDto(answer.getManagerName(), answer.getContent(),
            answer.getCreatedAt());
    }
}
