package com.example.SchoolLunchReport.ai.analyze.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.ai.analyze.dto.FoodEvaluationRequestDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodEvaluationResponseDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodInfoDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodNameDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodScoreDto;
import com.example.SchoolLunchReport.ai.analyze.service.AnalyzeService;
import com.example.SchoolLunchReport.ai.analyze.service.DjangoService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ANALYTICS_TEAM_URL + "/food-analyze")

public class AnalyzeController {

    private final AnalyzeService analyzeService;
    private final DjangoService djangoService;

    public AnalyzeController(AnalyzeService analyzeService, DjangoService djangoService) {
        this.analyzeService = analyzeService;
        this.djangoService = djangoService;
    }

    @GetMapping("/get-all-foods")
    public List<FoodNameDto> getAllFoods() {
        return analyzeService.getAllFoodNames();
    }

    @PostMapping("/get-food-info")
    public ResponseEntity<?> getFoodInfo(@RequestBody Map<String, Long> request) {
        Long foodId = request.get("food_id");
        FoodInfoDto foodInfo = analyzeService.getFoodInfo(foodId);
        if (foodInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food not found");
        }
        return ResponseEntity.ok(foodInfo);
    }

    @PostMapping("/get-food-count")
    public Map<String, Integer> getFoodCount(@RequestBody Map<String, Object> request) {
        Long foodId = Long.valueOf(request.get("food_id").toString());
        LocalDate startDate = LocalDate.parse(request.get("start_date").toString());
        LocalDate endDate = LocalDate.parse(request.get("end_date").toString());
        int count = analyzeService.countFoodInMenu(foodId, startDate, endDate);
        return Collections.singletonMap("count", count);
    }

    @PostMapping("/get-food-scores")
    public Map<String, Object> getFoodScores(@RequestBody Map<String, Object> request) {
        Long foodId = Long.valueOf(request.get("food_id").toString());
        LocalDate startDate = LocalDate.parse(request.get("start_date").toString());
        LocalDate endDate = LocalDate.parse(request.get("end_date").toString());

        System.out.println("요청 받은 파라미터: " + request);

        FoodScoreDto scores = analyzeService.getFeedbackScores(foodId, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        response.put("start_date", scores.getStartDate().toString());
        response.put("end_date", scores.getEndDate().toString());
        response.put("scores", scores.getScores());

        return response;
    }

    @PostMapping("/get-food-evaluation-summary")
    public ResponseEntity<?> getFoodEvaluationSummary(@RequestBody Map<String, Object> request) {
        try {
            Long foodId = Long.valueOf(request.get("food_id").toString());
            LocalDate startDate = LocalDate.parse(request.get("start_date").toString());
            LocalDate endDate = LocalDate.parse(request.get("end_date").toString());

            System.out.println("요청 받은 파라미터: " + request);

            FoodEvaluationRequestDto evaluationData = analyzeService.getFoodEvaluations(foodId,
                startDate, endDate);

            if (evaluationData.getEvaluations().isEmpty()) {
                return ResponseEntity.ok(Map.of(
                    "food_name", evaluationData.getFoodName(),
                    "summary", "해당 기간에 평가 데이터가 없습니다.",
                    "evaluation_count", 0
                ));
            }

            FoodEvaluationResponseDto djangoResponse = djangoService.sendEvaluationsToDjango(
                evaluationData);

            return ResponseEntity.ok(djangoResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}