package com.example.SchoolLunchReport.domain.product.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.domain.product.controller.dto.request.CreateMenuRequestDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.request.UpdateMenuRequestDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.CreateMenuResponseDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.DailyMenuResponseDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.FoodListResponseDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.UpdateMenuResponseDto;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import com.example.SchoolLunchReport.domain.product.service.ProductFacade;
import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ANALYTICS_TEAM_URL + "/products")
public class ProductController implements ProductControllerDocs {

    final ProductFacade productFacade;

    @Override
    @GetMapping("/foods")
    public ApiResponse<?> getFoodListAll() {
        // TODO 메서드명 변경
        Map<Category, List<FoodListResponseDto>> menuListByCategory = productFacade.getMenuListByCategory();
        return ApiResponse.success(SuccessType.SUCCESS, menuListByCategory);
    }

    @Override
    @GetMapping("/menus")
    public ApiResponse<?> getMenuList(
        @RequestParam(value = "startDate") LocalDate startDate,
        @RequestParam(value = "endDate") LocalDate endDate) {
        Map<LocalDate, DailyMenuResponseDto> menuListInBoundary = productFacade.getMenuListInBoundary(
            startDate, endDate);
        return ApiResponse.success(SuccessType.SUCCESS, menuListInBoundary);
    }

    @Override
    @PostMapping("/menus/{date}")
    public ApiResponse<?> createMenu(
        @PathVariable(value = "date") LocalDate date,
        @RequestParam(value = "mealType") MealType mealType,
        @RequestBody CreateMenuRequestDto createMenuRequestDto) {
        CreateMenuResponseDto createMenuResponseDto = productFacade.createMenu(date, mealType,
            createMenuRequestDto);
        return ApiResponse.success(SuccessType.CREATED, createMenuResponseDto);
    }

    @Override
    @PutMapping("/menus/{menuId}")
    public ApiResponse<?> updateMenu(
        @PathVariable(value = "menuId") Long menuId,
        @RequestBody UpdateMenuRequestDto updateMenuRequestDto) {
        UpdateMenuResponseDto createMenuResponseDto = productFacade.updateMenu(menuId,
            updateMenuRequestDto);
        return ApiResponse.success(SuccessType.CREATED, createMenuResponseDto);
    }

    @Override
    @DeleteMapping("/menus/{menuId}")
    public ApiResponse<?> deleteMenu(
        @PathVariable Long menuId
    ) {
        productFacade.deleteMenu(menuId);
        return ApiResponse.success(SuccessType.SUCCESS, menuId);
    }
}
