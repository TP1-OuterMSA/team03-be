package com.example.SchoolLunchReport.aireport.dto;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
@Getter
@Builder
public class ReportListResponseDto {
    private Long id;
    private String report;
    private String name;
    private LocalDateTime createdAt;
}