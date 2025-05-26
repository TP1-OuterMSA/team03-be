package com.example.SchoolLunchReport.domain.product.controller;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product 관련 API")
public interface ProductControllerDocs {

    @Operation(summary = "음식 전체 카테고리별 조회하기")
    ApiResponse<?> getFoodListAll();
}
