package com.example.SchoolLunchReport.statistics.controller;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.RequestParam;

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
}
