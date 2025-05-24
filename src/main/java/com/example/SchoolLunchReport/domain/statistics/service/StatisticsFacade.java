package com.example.SchoolLunchReport.domain.statistics.service;

import static com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType.MONTHLY;
import static com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType.WEEKLY;

import com.example.SchoolLunchReport.domain.product.food.repository.dto.FoodFrequencyDto;
import com.example.SchoolLunchReport.domain.product.food.service.FoodService;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.service.MenuService;
import com.example.SchoolLunchReport.domain.statistics.domain.rank.service.RankService;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.request.PeriodSpecRequestDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.CombinedRankMenuResponseDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.CombinedStatisticsResponse;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.StatisticsResponse;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.TrackingResponseDto;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.support.BoundaryMapper;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.domain.statistics.domain.desired.service.DesiredFoodService;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.CategoryScoreAvgDto;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.service.FeedBackService;
import com.example.SchoolLunchReport.domain.statistics.domain.rank.entity.FoodRank;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsFacade {

    final MenuService menuService;
    final FeedBackService feedBackService;
    final FoodService foodService;
    final DesiredFoodService desiredFoodService;
    final RankService rankService;
    final BoundaryMapper boundaryMapper;

    @Transactional(readOnly = true)
    public CombinedRankMenuResponseDto getRankMenu(PeriodType periodType, LocalDate date) {
        List<RankMenuResponseDto> topRankMenuResponseDtos = rankService.getTopRankMenu(periodType,
            date);
        List<RankMenuResponseDto> bottomRankMenuResponseDtos = rankService.getBottomRankMenu(
            periodType, date);
        return CombinedRankMenuResponseDto.of(topRankMenuResponseDtos, bottomRankMenuResponseDtos);
    }

    @Transactional(readOnly = true)
    public CombinedStatisticsResponse getStatistics(LocalDate date) {
        Boundary weeklyBoundary = boundaryMapper.mapBoundary(WEEKLY, date);
        Boundary monthlyBoundary = boundaryMapper.mapBoundary(MONTHLY, date);

        List<FeedBack> feedBackListWeekly = feedBackService.getFeedBackInBoundary(weeklyBoundary);
        List<FeedBack> feedBackListMonthly = feedBackService.getFeedBackInBoundary(monthlyBoundary);

        List<ScoreCount> scoreCountWeekly = rankService.getScoreCount(feedBackListWeekly);
        List<ScoreCount> scoreCountMonthly = rankService.getScoreCount(feedBackListMonthly);

        StatisticsResponse statisticsResponseWeekly = StatisticsResponse.builder()
            .periodType(WEEKLY)
            .scores(scoreCountWeekly)
            .build();

        StatisticsResponse statisticsResponseMonthly = StatisticsResponse.builder()
            .periodType(MONTHLY)
            .scores(scoreCountMonthly)
            .build();
        return new CombinedStatisticsResponse(statisticsResponseWeekly, statisticsResponseMonthly);
    }

    @Transactional(readOnly = true)
    public List<RankMenuResponseDto> getTrendingMenu(PeriodType periodType) {
        return rankService.getTrendingMenu(periodType);

    }

    public void calculateAndSaveRank(PeriodType periodType, LocalDate registerDate) {
        Boundary boundary = boundaryMapper.mapBoundary(periodType, registerDate);
        List<Menu> menuInBoundary = menuService.getMenuInBoundary(boundary);
        List<FeedBack> feedBackByMenu = feedBackService.getFeedBackByMenu(menuInBoundary);
        rankService.calculateAndSave(feedBackByMenu, periodType, registerDate);
    }

    @Transactional(readOnly = true)
    public List<FoodRank> getRankList(LocalDate registerDate, PeriodType periodType) {
        return rankService.getRankList(periodType, registerDate);
    }

    @Transactional(readOnly = true)
    public TrackingResponseDto getTrackingEvaluation(LocalDate localDate) {
        return feedBackService.getTrackingEvaluation(localDate);
    }

    @Transactional(readOnly = true)
    public List<DesiredFoodResponseDto> getDesiredFood(
        PeriodSpecRequestDto periodSpecRequestDto
    ) {
        Boundary boundary = boundaryMapper.mapBoundary(periodSpecRequestDto);
        return desiredFoodService.getDesiredFoodTopN(boundary, 3);
    }

    public List<CategoryScoreAvgDto> getCategoryScore(LocalDate startDate, LocalDate endDate) {
        return feedBackService.getCategoryScore(startDate, endDate);
    }

    public List<FoodFrequencyDto> getMenuFrequencyByCategory(
        PeriodSpecRequestDto periodSpecRequestDto) {
        Boundary boundary = boundaryMapper.mapBoundary(periodSpecRequestDto);
        return foodService.getFoodFrequencyByCategory(boundary);
    }
}
