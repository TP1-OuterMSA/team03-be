package com.example.SchoolLunchReport.domain.goal.domain;

import com.example.SchoolLunchReport.domain.goal.controller.dto.request.UpdateGoalRequestDto;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Goal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Food food;

    Double targetScore;
    Integer targetFrequency;

    @LastModifiedDate
    private LocalDate updatedAt;

    public Goal(Food food, Double targetScore, Integer targetFrequency) {
        this.food = food;
        this.targetScore = targetScore;
        this.targetFrequency = targetFrequency;
    }

    public void update(UpdateGoalRequestDto updateGoalRequestDto) {
        this.targetScore = updateGoalRequestDto.targetScore();
        this.targetFrequency = updateGoalRequestDto.targetFrequency();
    }
}
