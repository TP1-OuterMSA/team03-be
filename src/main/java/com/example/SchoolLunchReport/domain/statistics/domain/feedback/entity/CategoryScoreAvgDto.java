package com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity;

import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;

public record CategoryScoreAvgDto(
    Category category,
    Double average
) {

}
