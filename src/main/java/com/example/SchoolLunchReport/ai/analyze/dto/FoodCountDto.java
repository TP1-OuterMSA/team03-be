package com.example.SchoolLunchReport.ai.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class FoodCountDto {
    private Long foodId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int count;
}