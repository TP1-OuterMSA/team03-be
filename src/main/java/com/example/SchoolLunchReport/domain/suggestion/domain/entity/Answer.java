package com.example.SchoolLunchReport.domain.suggestion.domain.entity;

import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.UpdateAnswerRequestDto;
import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    Suggestion suggestion;

    String managerName;

    String content;

    @Builder
    public Answer(Suggestion suggestion, String managerName, String content) {
        this.suggestion = suggestion;
        this.managerName = managerName;
        this.content = content;
    }

    public void update(UpdateAnswerRequestDto updateAnswerRequestDto) {
        this.managerName = updateAnswerRequestDto.managerName();
        this.content = updateAnswerRequestDto.content();
    }
}
