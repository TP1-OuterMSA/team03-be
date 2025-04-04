package com.example.SchoolLunchReport.product.menu.domain.entity;

import com.example.SchoolLunchReport.global.common.BaseTimeEntity;
import com.example.SchoolLunchReport.product.menu.domain.type.MealType;
import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Getter;

@Entity
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private MealType mealType;
    private String evaluation;
}
