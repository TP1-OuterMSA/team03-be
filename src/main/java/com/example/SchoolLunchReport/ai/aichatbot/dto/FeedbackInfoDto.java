package com.example.SchoolLunchReport.ai.aichatbot.dto;

import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackInfoDto {
    private Long id;
    private String foodName;
    private Double score;
    private String evaluation;
    private String processedContent;
    private String createdDate;

    public static FeedbackInfoDto fromEntity(FeedBack feedback) {
        Food food = feedback.getFood();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return FeedbackInfoDto.builder()
                .id(feedback.getId())
                .foodName(food.getName())
                .score(feedback.getScore())
                .evaluation(feedback.getEvaluation())
                .createdDate(feedback.getCreatedAt().format(formatter))
                .processedContent(generateProcessedContent(feedback, food))
                .build();
    }

    private static String generateProcessedContent(FeedBack feedback, Food food) {
        return String.format("%s 음식은 5점 만점 중 %.1f점을 받았으며, '%s'라는 평가를 받았습니다. 이 평가는 %s에 작성되었습니다.",
                food.getName(),
                feedback.getScore(),
                feedback.getEvaluation(),
                feedback.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
    }
}