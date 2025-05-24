package com.example.SchoolLunchReport.ai.aireport.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiReportDataDto {
    private String period_data;
    private String menu_evaluation_data;
    private List<String> feed_back_evaluation_data;
    private Double average_calorie_data;
    private Double average_score_data;
    private String all_food_name_data;
}