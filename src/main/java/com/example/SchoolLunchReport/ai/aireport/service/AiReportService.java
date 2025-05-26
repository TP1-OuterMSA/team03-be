package com.example.SchoolLunchReport.ai.aireport.service;

import com.example.SchoolLunchReport.ai.aireport.dto.AiReportDataDto;
import com.example.SchoolLunchReport.ai.aireport.dto.AiReportRequestDto;
import com.example.SchoolLunchReport.ai.aireport.dto.ReportListResponseDto;
import com.example.SchoolLunchReport.ai.aireport.repository.ReportRepository;
import com.example.SchoolLunchReport.ai.aireport.dto.AiReportResponseDto;
import com.example.SchoolLunchReport.ai.aireport.entity.Report;
import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.domain.product.evaluation.entity.Evaluation;
import com.example.SchoolLunchReport.domain.product.evaluation.repository.EvaluationJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.FeedBackJpaRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AiReportService {

    private final MenuJpaRepository menuJpaRepository;
    private final EvaluationJpaRepository evaluationJpaRepository;
    private final FoodMenuJpaRepository foodMenuJpaRepository;
    private final FeedBackJpaRepo feedBackJpaRepository;
    private final FoodJpaRepository foodJpaRepository;
    private final ReportRepository reportRepository;
    private final ObjectMapper objectMapper;
    @Qualifier("aiReportRestTemplate")
    private final RestTemplate restTemplate;

    private static final int MAX_DATA_COUNT = 100;

    public List<ReportListResponseDto> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> ReportListResponseDto.builder()
                        .id(report.getId())
                        .report(report.getReport())
                        .name(report.getName())
                        .createdAt(report.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public AiReportResponseDto generateReport(AiReportRequestDto requestDto) {
        try {
            List<Menu> menus = menuJpaRepository.findByDateBetween(requestDto.getStart_date(),
                requestDto.getEnd_date());

            if (menus.isEmpty()) {
                return AiReportResponseDto.builder()
                    .error("해당 기간 동안의 학생식당 데이터가 존재하지 않습니다")
                    .build();
            }

            String periodData = requestDto.getStart_date().format(DateTimeFormatter.ISO_DATE) +
                "~" +
                requestDto.getEnd_date().format(DateTimeFormatter.ISO_DATE);

            String menuEvaluationData = getFormattedMenuEvaluationData(menus);

            List<String> feedBackEvaluationData = getFeedbackEvaluationData(menus);

            Double averageCalorieData = calculateAverageCalorieData(menus);
            Double averageScoreData = calculateAverageScoreData(menus);
            String allFoodNameData = getAllFoodNameData(menus);

            AiReportDataDto reportData = AiReportDataDto.builder()
                .period_data(periodData)
                .menu_evaluation_data(menuEvaluationData)
                .feed_back_evaluation_data(feedBackEvaluationData)
                .average_calorie_data(averageCalorieData)
                .average_score_data(averageScoreData)
                .all_food_name_data(allFoodNameData)
                .build();

            return sendReportToDjango(reportData);
        } catch (Exception e) {
            e.printStackTrace();
            return AiReportResponseDto.builder()
                .error("Error generating report: " + e.getMessage())
                .build();
        }
    }

    private String getFormattedMenuEvaluationData(List<Menu> menus) {
        List<String> evaluationList = new ArrayList<>();
        int count = 1;

        for (Menu menu : menus) {
            List<Evaluation> evaluations = evaluationJpaRepository.findByMenu(menu);
            for (Evaluation evaluation : evaluations) {
                if (evaluation.getEvaluation() != null && !evaluation.getEvaluation().isEmpty()) {
                    evaluationList.add(
                        String.format("%d번 평가: %s", count++, evaluation.getEvaluation()));
                }
            }
        }

        if (evaluationList.isEmpty()) {
            return "평가 데이터가 없습니다.";
        }

        if (evaluationList.size() > MAX_DATA_COUNT) {
            System.out.println(
                "평가 데이터가 " + evaluationList.size() + "개로 제한을 초과하여 " + MAX_DATA_COUNT + "개로 제한합니다.");
            Collections.shuffle(evaluationList);
            evaluationList = evaluationList.subList(0, MAX_DATA_COUNT);

            List<String> renumberedList = new ArrayList<>();
            for (int i = 0; i < evaluationList.size(); i++) {
                String evaluation = evaluationList.get(i);
                String content = evaluation.substring(evaluation.indexOf("번 평가: ") + 6);
                renumberedList.add(String.format("%d번 평가: %s", (i + 1), content));
            }
            evaluationList = renumberedList;
        }

        return String.join(", ", evaluationList);
    }

    private List<String> getFeedbackEvaluationData(List<Menu> menus) {
        List<String> feedbackList = new ArrayList<>();

        for (Menu menu : menus) {
            List<FoodMenu> foodMenus = foodMenuJpaRepository.findByMenu(menu);
            for (FoodMenu foodMenu : foodMenus) {
                List<FeedBack> feedBacks = feedBackJpaRepository.findByFoodMenu(foodMenu);
                for (FeedBack feedBack : feedBacks) {
                    if (feedBack.getFood() != null && feedBack.getScore() != null) {
                        String feedback = String.format("%s에 대한 평가 = 별점(5점만점): %.1f | 리뷰: %s",
                            feedBack.getFood().getName(),
                            feedBack.getScore(),
                            feedBack.getEvaluation() != null ? feedBack.getEvaluation() : "");
                        feedbackList.add(feedback);
                    }
                }
            }
        }

        if (feedbackList.size() > MAX_DATA_COUNT) {
            System.out.println(
                "피드백 데이터가 " + feedbackList.size() + "개로 제한을 초과하여 " + MAX_DATA_COUNT + "개로 제한합니다.");
            Collections.shuffle(feedbackList);
            feedbackList = feedbackList.subList(0, MAX_DATA_COUNT);
        }

        return feedbackList;
    }

    private Double calculateAverageCalorieData(List<Menu> menus) {
        double totalCalories = 0;
        int totalDays = 0;

        Map<LocalDate, List<Menu>> menusByDate = menus.stream()
            .collect(Collectors.groupingBy(Menu::getDate));

        for (Map.Entry<LocalDate, List<Menu>> entry : menusByDate.entrySet()) {
            double dailyCalories = 0;
            for (Menu menu : entry.getValue()) {
                List<FoodMenu> foodMenus = foodMenuJpaRepository.findByMenu(menu);
                for (FoodMenu foodMenu : foodMenus) {
                    if (foodMenu.getFood() != null && foodMenu.getFood().getCalorie() != null) {
                        dailyCalories += foodMenu.getFood().getCalorie();
                    }
                }
            }
            totalCalories += dailyCalories;
            totalDays++;
        }

        return totalDays > 0 ? totalCalories / totalDays : 0;
    }

    private Double calculateAverageScoreData(List<Menu> menus) {
        double totalScore = 0;
        int scoreCount = 0;

        for (Menu menu : menus) {
            List<FoodMenu> foodMenus = foodMenuJpaRepository.findByMenu(menu);
            for (FoodMenu foodMenu : foodMenus) {
                List<FeedBack> feedBacks = feedBackJpaRepository.findByFoodMenu(foodMenu);
                for (FeedBack feedBack : feedBacks) {
                    if (feedBack.getScore() != null) {
                        totalScore += feedBack.getScore();
                        scoreCount++;
                    }
                }
            }
        }
        return scoreCount > 0 ? totalScore / scoreCount : 0;
    }

    private String getAllFoodNameData(List<Menu> menus) {
        Set<String> uniqueFoodNames = new HashSet<>();
        for (Menu menu : menus) {
            List<FoodMenu> foodMenus = foodMenuJpaRepository.findByMenu(menu);
            for (FoodMenu foodMenu : foodMenus) {
                if (foodMenu.getFood() != null && foodMenu.getFood().getName() != null) {
                    uniqueFoodNames.add(foodMenu.getFood().getName());
                }
            }
        }
        return String.join(",", uniqueFoodNames);
    }

    private AiReportResponseDto sendReportToDjango(AiReportDataDto reportData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> requestMap = new HashMap<>();

            requestMap.put("period_data", reportData.getPeriod_data());
            requestMap.put("menu_evaluation_data", reportData.getMenu_evaluation_data());
            requestMap.put("feed_back_evaluation_data", reportData.getFeed_back_evaluation_data());
            requestMap.put("average_calorie_data", reportData.getAverage_calorie_data());
            requestMap.put("average_score_data", reportData.getAverage_score_data());
            requestMap.put("all_food_name_data", reportData.getAll_food_name_data());

            String jsonBody = objectMapper.writeValueAsString(requestMap);
            System.out.println("Sending JSON to Django: " + jsonBody);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            String djangoUrl = "http://k8s-msaservices-7d023f0bb9-676035063.ap-northeast-2.elb.amazonaws.com/api/team3/llmchatbot/create_report/";
            // String djangoUrl = "http://127.0.0.1:8000/api/team3/llmchatbot/create_report/";

            ResponseEntity<Map> response = restTemplate.exchange(
                djangoUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
            );

            System.out.println("Response from Django: " + response.getBody());

            String message = (String) Objects.requireNonNull(response.getBody()).get("message");
            String reportText = (String) response.getBody().get("report");

            String name = "기간: " + reportData.getPeriod_data() + " 동안의 ai보고서";

            Report savedReport = reportRepository.save(Report.builder()
                .report(reportText)
                .name(name)
                .build());

            return AiReportResponseDto.builder()
                .report_id(savedReport.getId())
                .message(message)
                .report(reportText)
                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return AiReportResponseDto.builder()
                .error("Error sending report to Django: " + e.getMessage())
                .build();
        }
    }

    public byte[] generatePdfFromReport(Long reportId) {
        try {
            Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));

            String html = convertMarkdownToHtml(report.getReport());

            return convertHtmlToPdf(html);
        } catch (Exception e) {
            System.err.println("PDF 생성 실패: " + e.getMessage());
            e.printStackTrace();

            try {
                return generateErrorPdf(
                    "보고서 ID: " + reportId + " PDF 생성 중 오류가 발생했습니다. 원인: " + e.getMessage());
            } catch (Exception fallbackError) {
                throw new RuntimeException("PDF 생성에 완전히 실패했습니다.", fallbackError);
            }
        }
    }

    private String convertMarkdownToHtml(String markdown) {
        try {
            MutableDataSet options = new MutableDataSet();
            try {
                options.set(Parser.EXTENSIONS, Collections.singletonList(
                    com.vladsch.flexmark.ext.tables.TablesExtension.create()
                ));
            } catch (NoClassDefFoundError e) {
                System.out.println("Tables extension not available, continuing without it");
            }

            Parser parser = Parser.builder(options).build();
            HtmlRenderer renderer = HtmlRenderer.builder(options).build();

            Node document = parser.parse(markdown);
            String html = renderer.render(document);
            html = fixHtmlForXmlCompatibility(html);

            return html;
        } catch (Exception e) {
            System.err.println("마크다운 변환 중 오류: " + e.getMessage());
            return "<p>" + markdown.replace("<", "&lt;").replace(">", "&gt;") + "</p>";
        }
    }

    private String fixHtmlForXmlCompatibility(String html) {
        html = html.replaceAll("<(meta|img|br|hr|input|link|col|base)([^>]*[^/])>", "<$1$2/>");
        html = html.replaceAll("</\\s*(meta|img|br|hr|input|link|col|base)\\s*>", "");
        html = html.replaceAll("<img(?![^>]*alt=)([^>]*)>", "<img alt=\"\" $1>");
        return html;
    }

    private byte[] convertHtmlToPdf(String html) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        File tempFontFile = null;

        try {
            String xhtml = wrapHtmlWithStyles(fixHtmlForXmlCompatibility(html));
            PdfRendererBuilder builder = new PdfRendererBuilder();

            tempFontFile = createTempFontFile("fonts/GmarketSansTTFMedium.ttf");

            builder.useFont(tempFontFile, "Gmarket Sans Medium");
            builder.withHtmlContent(xhtml, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("PDF 변환 중 오류: " + e.getMessage());
            e.printStackTrace();

            try {
                String fallbackHtml =
                    "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"/></head><body>" +
                        "<h1>보고서</h1><p>PDF 변환 중 오류가 발생했습니다. 원본 보고서를 표시합니다:</p>" +
                        "<pre>" + html.replace("<", "&lt;").replace(">", "&gt;") + "</pre>" +
                        "</body></html>";

                PdfRendererBuilder fallbackBuilder = new PdfRendererBuilder();
                fallbackBuilder.withHtmlContent(fallbackHtml, null);
                fallbackBuilder.toStream(outputStream);
                fallbackBuilder.run();

                return outputStream.toByteArray();
            } catch (Exception fallbackError) {
                String errorHtml = "<html><body><h1>PDF 생성 실패</h1></body></html>";
                PdfRendererBuilder errorBuilder = new PdfRendererBuilder();
                errorBuilder.withHtmlContent(errorHtml, null);
                errorBuilder.toStream(outputStream);
                errorBuilder.run();

                return outputStream.toByteArray();
            }
        } finally {
            outputStream.close();
            if (tempFontFile != null && tempFontFile.exists()) {
                tempFontFile.delete();
            }
        }
    }

    private File createTempFontFile(String fontPath) throws IOException {
        try (java.io.InputStream is = getClass().getClassLoader().getResourceAsStream(fontPath)) {
            if (is == null) {
                throw new IOException("Font resource not found: " + fontPath);
            }

            String extension = "";
            int lastDot = fontPath.lastIndexOf('.');
            if (lastDot > 0) {
                extension = fontPath.substring(lastDot);
            }

            File tempFile = File.createTempFile("font-", extension);
            tempFile.deleteOnExit();

            try (java.io.FileOutputStream out = new java.io.FileOutputStream(tempFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            return tempFile;
        }
    }

    private String wrapHtmlWithStyles(String htmlContent) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
            +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
            "    <style type=\"text/css\">\n" +
            "        body { font-family: 'Gmarket Sans Medium', sans-serif; margin: 40px; line-height: 1.6; }\n"
            +
            "        h1 { color: #333366; }\n" +
            "        h2 { color: #336699; border-bottom: 1px solid #ddd; padding-bottom: 5px; }\n" +
            "        h3 { color: #5588bb; }\n" +
            "        table { border-collapse: collapse; width: 100%; margin: 20px 0; }\n" +
            "        th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }\n" +
            "        th { background-color: #f2f2f2; }\n" +
            "        blockquote { background: #f9f9f9; border-left: 10px solid #ccc; margin: 1.5em 10px; padding: 0.5em 10px; }\n"
            +
            "        code { background: #f4f4f4; padding: 2px 4px; border-radius: 3px; }\n" +
            "        pre { background: #f4f4f4; padding: 10px; border-radius: 3px; overflow-x: auto; }\n"
            +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            htmlContent +
            "</body>\n" +
            "</html>";
    }

    // 오류 발생하면 -> 대체 PDF로 반환
    private byte[] generateErrorPdf(String errorMessage) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        String errorHtml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
            +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            "<head>\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n" +
            "    <style type=\"text/css\">\n" +
            "        body { font-family: Arial, sans-serif; margin: 40px; }\n" +
            "        .error { color: red; background: #ffeeee; padding: 20px; border: 1px solid #ffcccc; }\n"
            +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>PDF 생성 오류</h1>\n" +
            "    <div class=\"error\">" + errorMessage.replace("<", "&lt;").replace(">", "&gt;")
            + "</div>\n" +
            "    <p>관리자에게 문의하세요.</p>\n" +
            "</body>\n" +
            "</html>";

        try {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(errorHtml, null);
            builder.toStream(outputStream);
            builder.run();

            return outputStream.toByteArray();
        } finally {
            outputStream.close();
        }
    }
}