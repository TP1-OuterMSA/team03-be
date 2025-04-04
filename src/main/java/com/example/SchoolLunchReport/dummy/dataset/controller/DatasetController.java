package com.example.SchoolLunchReport.dummy.dataset.controller;
import com.example.SchoolLunchReport.dummy.dataset.dto.FeedBackDTO;
import com.example.SchoolLunchReport.dummy.dataset.dto.MenuWithFoodsDTO;
import com.example.SchoolLunchReport.dummy.dataset.service.DatasetService;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/team3/analytics/dataset")
public class DatasetController {

    private final DatasetService datasetService;
    public DatasetController(DatasetService datasetService) {
        this.datasetService = datasetService;
    }
    @PostMapping("/createfood")
    public ResponseEntity<Map<String, Object>> createFoodData(@RequestBody List<Food> foods) {
        int count = datasetService.createFoodData(foods);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "음식 데이터가 성공적으로 생성되었습니다");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/createmenu")
    public ResponseEntity<Map<String, Object>> createMenuWithFoods(@RequestBody List<MenuWithFoodsDTO> menuWithFoodsList) {
        int count = datasetService.createMenuWithFoods(menuWithFoodsList);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "메뉴 및 푸드메뉴 데이터가 성공적으로 생성되었습니다");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/createfeedback")
    public ResponseEntity<Map<String, Object>> createFeedback(@RequestBody List<FeedBackDTO> feedbacks) {
        int count = datasetService.createFeedback(feedbacks);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "피드백 데이터가 성공적으로 생성되었습니다");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/createfeedback/random")
    public ResponseEntity<Map<String, Object>> createRandomFeedback() {
        int count = datasetService.createRandomFeedback();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "랜덤 피드백 데이터가 성공적으로 생성되었습니다");
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("allmenu")
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = datasetService.getAllMenus();
        return ResponseEntity.ok(menus);
    }
}