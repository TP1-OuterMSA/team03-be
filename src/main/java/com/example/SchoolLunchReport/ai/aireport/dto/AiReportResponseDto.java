package com.example.SchoolLunchReport.ai.aireport.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiReportResponseDto {
    private Long report_id;
    private String message;
    private String error;
    private String report;
}