package com.example.SchoolLunchReport.domain.statistics.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.domain.product.food.repository.dto.FoodFrequencyDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.request.PeriodSpecRequestDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.CombinedRankMenuResponseDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.TrackingResponseDto;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.dto.CategoryScoreAvgDto;
import com.example.SchoolLunchReport.domain.statistics.service.StatisticsFacade;
import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ANALYTICS_TEAM_URL + "/statistics")
@RequiredArgsConstructor
public class StatisticsController implements StatisticsControllerDocs {

    final StatisticsFacade statisticsFacade;


    @Override
    @GetMapping("/rank")
    public ApiResponse<?> getRankMenu(
        @RequestParam PeriodType periodType,
        @RequestParam(required = false) LocalDate date
    ) {
        //TODO 랭크 음식 몇개를 조회할지를 parameter 받도록 변경.
        if (Objects.isNull(date)) {
            date = LocalDate.now();
        }

        CombinedRankMenuResponseDto combinedRankMenuResponseDto = statisticsFacade.getRankMenu(
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

        return ApiResponse.success(SuccessType.SUCCESS, statisticsFacade.getStatistics(date));
    }

    @Override
    @GetMapping("/trending")
    public ApiResponse<List<RankMenuResponseDto>> getTrendingMenu(
        PeriodType periodType
    ) {

        return ApiResponse.success(SuccessType.SUCCESS, statisticsFacade.getTrendingMenu(
            periodType));
    }

    @Override
    @GetMapping("/tracking")
    public ApiResponse<TrackingResponseDto> getTrackingEvaluation(
        LocalDate localDate
    ) {
        return ApiResponse.success(SuccessType.SUCCESS, statisticsFacade.getTrackingEvaluation(
            localDate));
    }

    @GetMapping("/desired-food/{periodType}")
    public ApiResponse<List<DesiredFoodResponseDto>> getDesiredFood(
        @Valid @ModelAttribute PeriodSpecRequestDto periodSpecRequestDto
    ) {
        List<DesiredFoodResponseDto> desiredFoodResponseDto = statisticsFacade.getDesiredFood(
            periodSpecRequestDto);
        return ApiResponse.success(SuccessType.SUCCESS, desiredFoodResponseDto);
    }

    @Override
    @GetMapping("/category")
    public ApiResponse<?> getCategoryScore(
        @RequestParam(name = "startDate") LocalDate startDate,
        @RequestParam(name = "endDate") LocalDate endDate
    ) {
        List<CategoryScoreAvgDto> categoryScore = statisticsFacade.getCategoryScore(startDate,
            endDate);
        return ApiResponse.success(SuccessType.SUCCESS, categoryScore);
    }

    @Override
    @GetMapping("/food/frequency")
    public ApiResponse<?> getMenuFrequencyByCategory(
        @Valid @ModelAttribute PeriodSpecRequestDto periodSpecRequestDto
    ) {
        List<FoodFrequencyDto> menuFrequencyByCategory = statisticsFacade.getMenuFrequencyByCategory(
            periodSpecRequestDto);
        return ApiResponse.success(SuccessType.SUCCESS, menuFrequencyByCategory);
    }
}
