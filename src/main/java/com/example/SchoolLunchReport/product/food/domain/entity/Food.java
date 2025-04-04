package com.example.SchoolLunchReport.product.food.domain.entity;
import com.example.SchoolLunchReport.product.food.domain.type.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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
    private Category category;
    private String nutrition;
    private Double calorie;
    private String allergy;
    @Builder
    public Food(String name, Category category, String nutrition, Double calorie,
        String allergy) {
        this.name = name;
        this.category = category;
        this.nutrition = nutrition;
        this.calorie = calorie;
        this.allergy = allergy;
    }
}
