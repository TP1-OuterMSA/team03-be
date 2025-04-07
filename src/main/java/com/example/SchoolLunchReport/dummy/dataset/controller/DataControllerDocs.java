package com.example.SchoolLunchReport.dummy.dataset.controller;


import com.example.SchoolLunchReport.dummy.dataset.dto.FeedBackDTO;
import com.example.SchoolLunchReport.dummy.dataset.dto.MenuWithFoodsDTO;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "데이터 초기화 관련 API")
public interface DataControllerDocs {

    @Operation(summary = "음식 초기화")
    ResponseEntity<Map<String, Object>> createFoodData(@RequestBody List<Food> foods);

    @Operation(summary = "메뉴 초기화")
    ResponseEntity<Map<String, Object>> createMenuWithFoods(
        @RequestBody List<MenuWithFoodsDTO> menuWithFoodsList);

    @Operation(summary = "피드백 초기화")
    ResponseEntity<Map<String, Object>> createFeedback(
        @RequestBody List<FeedBackDTO> feedbacks);

    @Operation(summary = "랜덤 피드벡 초기화")
    ResponseEntity<Map<String, Object>> createRandomFeedback();

    @Operation(summary = "모든 메뉴 조회")
    ResponseEntity<List<Menu>> getAllMenus();


}
