package com.example.SchoolLunchReport.dummy.dataset.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;
import com.example.SchoolLunchReport.dummy.dataset.dto.FeedBackDTO;
import com.example.SchoolLunchReport.dummy.dataset.dto.MenuWithFoodsDTO;
import com.example.SchoolLunchReport.dummy.dataset.service.DatasetService;
import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.DesiredFood;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.service.StatisticsFacade;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ANALYTICS_TEAM_URL + "/dataset")
public class DatasetController implements DataControllerDocs {

    private final DatasetService datasetService;
    private final StatisticsFacade statisticsFacade;

    @PostMapping("/createfood")
    public ResponseEntity<Map<String, Object>> createFoodData(@RequestBody List<Food> foods) {
        int count = datasetService.createFoodData(foods);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "음식 데이터가 성공적으로 생성되었습니다..");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createmenu")
    public ResponseEntity<Map<String, Object>> createMenuWithFoods(
        @RequestBody List<MenuWithFoodsDTO> menuWithFoodsList) {
        int count = datasetService.createMenuWithFoods(menuWithFoodsList);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "메뉴 및 푸드메뉴 데이터가 성공적으로 생성되었습니다..");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createfeedback")
    public ResponseEntity<Map<String, Object>> createFeedback(
        @RequestBody List<FeedBackDTO> feedbacks) {
        int count = datasetService.createFeedback(feedbacks);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "피드백 데이터가 성공적으로 생성되었습니다..");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createfeedback/random")
    public ResponseEntity<Map<String, Object>> createRandomFeedback(LocalDate registerDate) {
        int count = datasetService.createRandomFeedback(registerDate);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "랜덤 피드백 데이터가 성공적으로 생성되었습니다..");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allmenu")
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = datasetService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @Override
    @PostMapping("/rank")
    public ApiResponse<?> createRank(
        @RequestParam LocalDate registerDate,
        PeriodType periodType
    ) {
        statisticsFacade.calculateAndSaveRank(periodType, registerDate);
        return ApiResponse.success(SuccessType.SUCCESS);
    }

    @Override
    @GetMapping("/rank")
    public ApiResponse<?> getRankList(LocalDate registerDate, PeriodType periodType) {
        List<FoodRank> rankList = statisticsFacade.getRankList(registerDate, periodType);
        return ApiResponse.success(SuccessType.SUCCESS, rankList);
    }
    @Override
    @PostMapping("/desired-foods")
    public ApiResponse<?> createDesiredFoods() {
        List<DesiredFood> desiredFoods = datasetService.createDesiredFoods();
        return ApiResponse.success(SuccessType.SUCCESS,desiredFoods);
    }


    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "test4 7:11");
        return ResponseEntity.ok(response);
    }
}
