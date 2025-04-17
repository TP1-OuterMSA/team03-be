package com.example.SchoolLunchReport.statistics.domain.feedback.entity;

import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double score;
    @ManyToOne
    private FoodMenu foodMenu;

    @Builder
    public FeedBack(Double score, FoodMenu foodMenu, LocalDate createdAt) {
        this.score = score;
        this.foodMenu = foodMenu;
        this.createdAt = createdAt;
    }

    public Food getFood() {
        return foodMenu.getFood();
    }
}
