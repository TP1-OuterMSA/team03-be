package com.example.SchoolLunchReport.analyze.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodEvaluationRequestDto {
    private String foodName;
    private List<String> evaluations;
}