package com.example.SchoolLunchReport.statistics.domain.feedback.entity;

import com.example.SchoolLunchReport.product.food.domain.type.Category;

public record CategoryScoreSumDto(
    Category category,
    Double totalScore
) {

}
