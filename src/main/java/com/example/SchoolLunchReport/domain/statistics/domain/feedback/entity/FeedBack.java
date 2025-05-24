package com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity;

import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
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
public class FeedBack extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double score;
    @ManyToOne
    private FoodMenu foodMenu;
    private String evaluation;
    @Builder
    public FeedBack(Double score, FoodMenu foodMenu, String evaluation) {
        this.score = score;
        this.foodMenu = foodMenu;
        this.evaluation = evaluation;
    }

    public Food getFood() {
        return foodMenu.getFood();
    }
}
