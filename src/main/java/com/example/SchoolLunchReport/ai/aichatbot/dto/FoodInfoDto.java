package com.example.SchoolLunchReport.ai.aichatbot.dto;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodInfoDto {
    private Long id;
    private String name;
    private String category;
    private String nutrition;
    private Double calorie;
    private String allergy;
    private String processedContent;

    public static FoodInfoDto fromEntity(Food food) {
        return FoodInfoDto.builder()
                .id(food.getId())
                .name(food.getName())
                .category(food.getCategory().toString())
                .nutrition(food.getNutrition())
                .calorie(food.getCalorie())
                .allergy(food.getAllergy())
                .processedContent(generateProcessedContent(food))
                .build();
    }

    private static String generateProcessedContent(Food food) {
        return String.format("%s 음식의 영양소는 %s이며, 칼로리는 %.2f kcal입니다. 알러지 정보는 %s입니다. 카테고리는 %s입니다.",
                food.getName(),
                food.getNutrition(),
                food.getCalorie(),
                food.getAllergy(),
                food.getCategory().toString());
    }
}