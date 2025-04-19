package com.example.SchoolLunchReport.aireport.controller;
import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;
import com.example.SchoolLunchReport.aireport.dto.AiReportRequestDto;
import com.example.SchoolLunchReport.aireport.dto.AiReportResponseDto;
import com.example.SchoolLunchReport.aireport.service.AiReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ANALYTICS_TEAM_URL + "/aireport")
@RequiredArgsConstructor
public class AiReportController {
    private final AiReportService aiReportService;
    @PostMapping("/generate")
    public ResponseEntity<AiReportResponseDto> generateReport(@RequestBody AiReportRequestDto requestDto) {
        AiReportResponseDto responseDto = aiReportService.generateReport(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
