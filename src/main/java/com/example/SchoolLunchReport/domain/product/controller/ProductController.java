package com.example.SchoolLunchReport.domain.product.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.domain.product.controller.dto.FoodListResponseDto;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.product.service.ProductFacade;
import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ANALYTICS_TEAM_URL + "/products")
public class ProductController implements ProductControllerDocs {

    final ProductFacade productFacade;

    @Override
    @GetMapping("/foods")
    public ApiResponse<?> getFoodListAll() {
        Map<Category, List<FoodListResponseDto>> menuListByCategory = productFacade.getMenuListByCategory();
        return ApiResponse.success(SuccessType.SUCCESS, menuListByCategory);
    }
}
