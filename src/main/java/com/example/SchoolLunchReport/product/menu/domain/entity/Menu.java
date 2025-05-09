package com.example.SchoolLunchReport.product.menu.domain.entity;

import com.example.SchoolLunchReport.product.menu.domain.type.MealType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;

    @Enumerated(value = EnumType.STRING)
    private MealType mealType;
}