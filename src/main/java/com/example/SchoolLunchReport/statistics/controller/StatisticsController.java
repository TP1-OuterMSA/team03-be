package com.example.SchoolLunchReport.statistics.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import com.example.SchoolLunchReport.statistics.controller.dto.response.CombinedRankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.service.StatisticsService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ANALYTICS_TEAM_URL + "/statistics")
@RequiredArgsConstructor
public class StatisticsController implements StatisticsControllerDocs {

    final StatisticsService statisticsService;

    @Override
    @GetMapping("/rank")
    public ApiResponse<?> getRankMenu(
        @RequestParam PeriodType periodType,
        @RequestParam(required = false) LocalDate date
    ) {
        if (Objects.isNull(date)) {
            date = LocalDate.now();
        }

        CombinedRankMenuResponseDto combinedRankMenuResponseDto = statisticsService.getRankMenu(
            periodType, date);
        return ApiResponse.success(SuccessType.SUCCESS, combinedRankMenuResponseDto);
    }

    @GetMapping
    public ApiResponse<?> getStatistics(
        @RequestParam(required = false) LocalDate date
    ) {
        if (Objects.isNull(date)) {
            date = LocalDate.now();
        }

        return ApiResponse.success(SuccessType.SUCCESS, statisticsService.getStatistics(date));
    }

    @Override
    @GetMapping("/trending")
    public ApiResponse<List<RankMenuResponseDto>> getTrendingMenu(
        PeriodType periodType
    ) {

        return ApiResponse.success(SuccessType.SUCCESS, statisticsService.getTrendingMenu(
            periodType));
    }

}
