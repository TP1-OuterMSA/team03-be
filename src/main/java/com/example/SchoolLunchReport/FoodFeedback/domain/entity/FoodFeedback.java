package com.example.SchoolLunchReport.FoodFeedback.domain.entity;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class FoodFeedback {

    @Id
    private Long id;
    @ManyToOne
    private Menu menu;

    @ManyToOne
    private Food food;
}
