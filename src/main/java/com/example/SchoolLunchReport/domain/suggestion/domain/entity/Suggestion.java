package com.example.SchoolLunchReport.domain.suggestion.domain.entity;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.UpdateSuggestionRequestDto;
import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Suggestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String nickName;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String foodName;

    @Builder
    public Suggestion(String title, String nickName, Category category, String content,
        String foodName) {
        this.title = title;
        this.nickName = nickName;
        this.category = category;
        this.content = content;
        this.foodName = foodName;
    }

    public void update(UpdateSuggestionRequestDto updateSuggestionRequestDto) {
        this.title = updateSuggestionRequestDto.title();
        this.nickName = updateSuggestionRequestDto.nickName();
        this.category = updateSuggestionRequestDto.category();
        this.content = updateSuggestionRequestDto.content();
        this.foodName = updateSuggestionRequestDto.foodName();
    }
}
