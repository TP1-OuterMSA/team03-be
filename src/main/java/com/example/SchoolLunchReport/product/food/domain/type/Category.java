package com.example.SchoolLunchReport.product.food.domain.type;
import java.util.Arrays;
public enum Category {
    RICE, SOUP, MAIN_DISH, SIDE_DISH, DESSERT;
    public static Category getInstance(String name) {
        return Arrays.stream(values())
            .filter(type -> type.name().equals(name))
            .findFirst()
            .orElseThrow(
                IllegalArgumentException::new);
    }
}