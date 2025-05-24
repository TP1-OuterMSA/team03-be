package com.example.SchoolLunchReport.ai.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FoodInfoDto {
    private String name;
    private String nutrition;
    private Double calorie;
    private String allergy;
    private String category;
    private String subCategory;
}
