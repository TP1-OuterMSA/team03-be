package com.example.SchoolLunchReport.statistics.service;

import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.MONTHLY;
import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.WEEKLY;

import com.example.SchoolLunchReport.product.menu.service.MenuService;
import com.example.SchoolLunchReport.statistics.controller.dto.response.CombinedRankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.CombinedStatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.controller.dto.response.TrackingResponseDto;
import com.example.SchoolLunchReport.statistics.domain.desired.service.DesiredFoodService;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.CategoryScoreAvgDto;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.rank.service.RankService;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.support.FeedBackReader;
import com.example.SchoolLunchReport.statistics.support.FeedBackTracker;
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
    final FeedBackReader feedBackReader;
    final FeedBackTracker feedBackTracker;
    final DesiredFoodService desiredFoodService;
    final RankService rankService;

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

        List<FeedBack> feedBackListWeekly = feedBackReader.getFeedBackInBoundary(date, WEEKLY);
        List<FeedBack> feedBackListMonthly = feedBackReader.getFeedBackInBoundary(date, MONTHLY);

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
        LocalDate preDate = periodType.getStartOfPreviousPeriod(registerDate);
        List<FeedBack> feedBackList = feedBackReader.getFeedBackInBoundary(preDate, registerDate);
        rankService.calculateAndSave(feedBackList, periodType, preDate, registerDate);
    }

    @Transactional(readOnly = true)
    public List<FoodRank> getRankList(LocalDate registerDate, PeriodType periodType) {
        return rankService.getRankList(periodType, registerDate);
    }

    @Transactional(readOnly = true)
    public TrackingResponseDto getTrackingEvaluation(LocalDate localDate) {
        return feedBackTracker.getTrackingEvaluation(localDate);
    }

    @Transactional(readOnly = true)
    public List<DesiredFoodResponseDto> getDesiredFood(
        LocalDate startDate, LocalDate endDate
    ) {
        return desiredFoodService.getDesiredFoodTopN(
            startDate, endDate, 3);
    }

    public List<CategoryScoreAvgDto> getCategoryScore(LocalDate startDate, LocalDate endDate) {
        return feedBackReader.getCategoryScore(startDate,
            endDate);
    }
}
