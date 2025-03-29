package com.example.SchoolLunchReport.statistics.feedback.domain.entity;

import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class FeedBack extends BaseTimeEntity {

    @Id
    private Long id;
    private Double score;

    @ManyToOne
    private Food food;

    @Builder
    public FeedBack(Long id, Double score, Food food, LocalDate createdAt) {
        this.id = id;
        this.score = score;
        this.food = food;
        this.createdAt = createdAt;
    }
}
