package com.example.SchoolLunchReport.aireport.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
@Getter
@Setter
public class AiReportRequestDto {
    private LocalDate start_date;
    private LocalDate end_date;
}