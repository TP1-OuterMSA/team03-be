package com.example.SchoolLunchReport.aireport.controller;
import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;
import com.example.SchoolLunchReport.aireport.dto.AiReportRequestDto;
import com.example.SchoolLunchReport.aireport.dto.AiReportResponseDto;
import com.example.SchoolLunchReport.aireport.dto.ReportDownloadRequestDto;
import com.example.SchoolLunchReport.aireport.service.AiReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping("/download")
    public ResponseEntity<byte[]> downloadReport(@RequestBody ReportDownloadRequestDto requestDto) {
        try {
            byte[] pdfBytes = aiReportService.generatePdfFromReport(requestDto.getReport_id());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "report.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
