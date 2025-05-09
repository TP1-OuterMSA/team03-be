package com.example.SchoolLunchReport.product.food.domain.entity;

import com.example.SchoolLunchReport.product.food.domain.type.Category;
import com.example.SchoolLunchReport.product.food.domain.type.SubCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Enumerated(value = EnumType.STRING)
    private SubCategory subCategory;

    private String nutrition;

    private Double calorie;

    private String allergy;

    @Builder
    public Food(String name, Category category, String nutrition, Double calorie,
        String allergy, SubCategory subCategory) {
        this.name = name;
        this.category = category;
        this.nutrition = nutrition;
        this.calorie = calorie;
        this.allergy = allergy;
        this.subCategory = subCategory;
    }
}
