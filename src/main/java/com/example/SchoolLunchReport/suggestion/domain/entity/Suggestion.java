package com.example.SchoolLunchReport.suggestion.domain.entity;

import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.domain.type.Category;
import com.example.SchoolLunchReport.suggestion.controller.dto.request.UpdateSuggestionRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne(optional = true)
    private Food food;

    @Builder
    public Suggestion(String title, String nickName, Category category, String content,
        Food food) {
        this.title = title;
        this.nickName = nickName;
        this.category = category;
        this.content = content;
        this.food = food;
    }

    public void update(UpdateSuggestionRequestDto updateSuggestionRequestDto, Food food) {
        this.title = updateSuggestionRequestDto.title();
        this.nickName = updateSuggestionRequestDto.nickName();
        this.category = updateSuggestionRequestDto.category();
        this.content = updateSuggestionRequestDto.content();
        this.food = food;
    }
}
