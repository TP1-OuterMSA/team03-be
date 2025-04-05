package com.example.SchoolLunchReport.statistics.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.domain.type.Period;
import com.example.SchoolLunchReport.statistics.service.StatisticsService;
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
        @RequestParam Period period
    ) {
        RankMenuResponseDto rankMenuResponseDto = statisticsService.getRankMenu(period);
        return ApiResponse.success(SuccessType.SUCCESS, rankMenuResponseDto);
    }

    @GetMapping
    public ApiResponse<?> getStatistics() {
        return ApiResponse.success(SuccessType.SUCCESS, statisticsService.getStatistics());
    }

}
