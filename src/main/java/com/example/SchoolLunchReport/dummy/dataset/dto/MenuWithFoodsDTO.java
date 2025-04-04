package com.example.SchoolLunchReport.dummy.dataset.dto;
import com.example.SchoolLunchReport.product.menu.domain.type.MealType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MenuWithFoodsDTO {
    private LocalDate date;
    private MealType mealType;
    private String evaluation;
}