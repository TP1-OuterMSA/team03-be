package com.example.SchoolLunchReport.ai.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class FoodScoreDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Double> scores;
}