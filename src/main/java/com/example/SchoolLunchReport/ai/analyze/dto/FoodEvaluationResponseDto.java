package com.example.SchoolLunchReport.ai.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodEvaluationResponseDto {
    private String foodName;
    private String summary;
    private int evaluationCount;
    private String error;
}