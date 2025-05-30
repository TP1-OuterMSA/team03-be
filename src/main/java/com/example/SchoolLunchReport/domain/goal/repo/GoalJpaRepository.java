package com.example.SchoolLunchReport.domain.goal.repo;

import com.example.SchoolLunchReport.domain.goal.domain.Goal;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalJpaRepository extends JpaRepository<Goal, Long> {

    Goal findByFood(Food food);
}
