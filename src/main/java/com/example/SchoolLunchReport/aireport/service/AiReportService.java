package com.example.SchoolLunchReport.aireport.service;
import com.example.SchoolLunchReport.aireport.dto.AiReportDataDto;
import com.example.SchoolLunchReport.aireport.dto.AiReportRequestDto;
import com.example.SchoolLunchReport.aireport.dto.AiReportResponseDto;
import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.product.evaluation.entity.Evaluation;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.product.evaluation.repository.EvaluationJpaRepository;
import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.statistics.repository.FeedBackJpaRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class AiReportService {
    private final MenuJpaRepository menuJpaRepository;
    private final EvaluationJpaRepository evaluationJpaRepository;
    private final FoodMenuJpaRepository foodMenuJpaRepository;
    private final FeedBackJpaRepo feedBackJpaRepository;
    private final FoodJpaRepository foodJpaRepository;
    private final ObjectMapper objectMapper;
    @Qualifier("aiReportRestTemplate")
    private final RestTemplate restTemplate;

    private static final int MAX_DATA_COUNT = 100;

    public AiReportResponseDto generateReport(AiReportRequestDto requestDto) {
        try {
            List<Menu> menus = menuJpaRepository.findByDateBetween(requestDto.getStart_date(), requestDto.getEnd_date());

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
                    evaluationList.add(String.format("%d번 평가: %s", count++, evaluation.getEvaluation()));
                }
            }
        }

        if (evaluationList.isEmpty()) return "평가 데이터가 없습니다.";

        if (evaluationList.size() > MAX_DATA_COUNT) {
            System.out.println("평가 데이터가 " + evaluationList.size() + "개로 제한을 초과하여 " + MAX_DATA_COUNT + "개로 제한합니다.");
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
            System.out.println("피드백 데이터가 " + feedbackList.size() + "개로 제한을 초과하여 " + MAX_DATA_COUNT + "개로 제한합니다.");
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

            ResponseEntity<Map> response = restTemplate.exchange(
                    djangoUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            System.out.println("Response from Django: " + response.getBody());

            String message = (String) response.getBody().get("message");
            String report = (String) response.getBody().get("report");

            return AiReportResponseDto.builder()
                    .message(message)
                    .report(report)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return AiReportResponseDto.builder()
                    .error("Error sending report to Django: " + e.getMessage())
                    .build();
        }
    }
}