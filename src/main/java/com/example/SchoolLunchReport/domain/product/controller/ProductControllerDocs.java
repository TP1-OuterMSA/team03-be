package com.example.SchoolLunchReport.domain.product.controller;

import com.example.SchoolLunchReport.domain.product.controller.dto.request.CreateMenuRequestDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.request.UpdateMenuRequestDto;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import com.example.SchoolLunchReport.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;

@Tag(name = "Product 관련 API")
public interface ProductControllerDocs {

    @Operation(summary = "음식 전체 카테고리별 조회하기")
    ApiResponse<?> getFoodListAll();

    @Operation(summary = "주차별 식단 조회")
    ApiResponse<?> getMenuList(
        LocalDate startDate,
        LocalDate endDate
    );

    @Operation(summary = "식단 만들기")
    ApiResponse<?> createMenu(
        LocalDate date,
        MealType mealType,
        CreateMenuRequestDto createMenuRequestDto
    );

    @Operation(summary = "식단 put api")
    ApiResponse<?> updateMenu(
        Long menuId,
        UpdateMenuRequestDto updateMenuRequestDto
    );

    @Operation(summary = "식단 delete api")
    ApiResponse<?> deleteMenu(Long menuId);
}
