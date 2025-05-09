package com.example.SchoolLunchReport.statistics.controller;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.request.PeriodSpecDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.TrackingResponseDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "순위 관련된 api")
public interface StatisticsControllerDocs {

    @Operation(summary = "인기, 비인기 5개 메뉴 조회 api")
    ApiResponse<?> getRankMenu(PeriodType periodType, LocalDate conditionDate);

    @Operation(summary = "주간, 월간 통계 조회 api")
    ApiResponse<?> getStatistics(
        LocalDate date
    );

    @Operation(summary = "급 상승 메뉴 조회")
    ApiResponse<?> getTrendingMenu(PeriodType periodType);

    @Operation(summary = "주간, 월간 추이 조회")
    ApiResponse<TrackingResponseDto> getTrackingEvaluation(
        LocalDate localDate
    );

    @Operation(summary = "먹고 싶은 메뉴 조회")
    ApiResponse<List<DesiredFoodResponseDto>> getDesiredFood(
        PeriodSpecDto periodSpecDto);

    @Operation(summary = "카테고리별 평점 조회 API")
    ApiResponse<?> getCategoryScore(LocalDate startDate, LocalDate endDate);

    @Operation(summary = "카테고리별 빈도 통계 조회 API")
    ApiResponse<?> getMenuFrequencyByCategory(
        PeriodSpecDto periodSpecDto
    );
}
