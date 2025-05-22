package com.example.SchoolLunchReport.product.menu.domain.type;

import java.util.Arrays;

public enum MealType {
    BREAK_FAST, LUNCH, DINNER

    public static MealType getInstance(String mealType) {
        return Arrays.stream(MealType.values())
            .filter(m -> m.name().equalsIgnoreCase(mealType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 MealType입니다 : " + mealType));
    }
}
