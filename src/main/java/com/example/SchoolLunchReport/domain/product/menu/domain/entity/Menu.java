package com.example.SchoolLunchReport.domain.product.menu.domain.entity;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Enumerated(value = EnumType.STRING)
    private MealType mealType;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodMenu> foodMenuList;

    @Nullable
    @Column
    private String hashTags; // , 로 구분

    @Builder
    public Menu(LocalDate date, MealType mealType, String hashTag) {
        this.date = date;
        this.mealType = mealType;
        this.hashTags = hashTag;
    }

    public void updateHashtag(String newHashTags) {
        hashTags = newHashTags;
    }
}