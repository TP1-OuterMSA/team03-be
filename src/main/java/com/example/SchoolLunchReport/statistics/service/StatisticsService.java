package com.example.SchoolLunchReport.statistics.service;

import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.MONTHLY;
import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.WEEKLY;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.response.CombinedRankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.response.CombinedStatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.response.StatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.controller.dto.response.response.TrackingResponseDto;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.support.FeedBackReader;
import com.example.SchoolLunchReport.statistics.support.FeedBackTracker;
import com.example.SchoolLunchReport.statistics.support.RankCalculator;
import com.example.SchoolLunchReport.statistics.support.RankFilter;
import com.example.SchoolLunchReport.statistics.support.RankImpl;
import com.example.SchoolLunchReport.statistics.support.RankReader;
import com.example.SchoolLunchReport.statistics.support.RankSaver;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    final FeedBackReader feedBackReader;
    final RankImpl rankImpl;
    final RankReader rankReader;
    final RankCalculator rankCalculator;
    final RankSaver rankSaver;
    final RankFilter rankFilter;
    final FeedBackTracker feedBackTracker;
    
    @Transactional(readOnly = true)
    public CombinedRankMenuResponseDto getRankMenu(PeriodType periodType, LocalDate date) {
        LocalDate targetDate = periodType.getStartOfThisPeriod(date);

        List<FoodRank> top5FoodRank = rankReader.getTop5FoodRank(periodType, targetDate);
        List<RankMenuResponseDto> topRankMenuResponseDtos = rankImpl.getRankMenuResponseDtoList(
            top5FoodRank);

        List<FoodRank> bottom5FoodRank = rankReader.getBottom5FoodRank(periodType, targetDate);
        List<RankMenuResponseDto> bottomRankMenuResponseDtos = rankImpl.getRankMenuResponseDtoList(
            bottom5FoodRank);

        return CombinedRankMenuResponseDto.of(topRankMenuResponseDtos, bottomRankMenuResponseDtos);
    }


    @Transactional(readOnly = true)
    public CombinedStatisticsResponse getStatistics(LocalDate date) {

        LocalDate startWeekDate = WEEKLY.getStartOfThisPeriod(date);
        LocalDate endWeekDate = WEEKLY.getStartOfPreviousPeriod(date);
        List<FeedBack> feedBackListWeekly = feedBackReader.getFeedBackInBoundary(endWeekDate,
            startWeekDate);

        LocalDate startMonthDate = MONTHLY.getStartOfPreviousPeriod(date);
        LocalDate endMonthDate = MONTHLY.getStartOfThisPeriod(date);
        List<FeedBack> feedBackListMonthly = feedBackReader.getFeedBackInBoundary(startMonthDate,
            endMonthDate);

        List<ScoreCount> scoreCountWeekly = rankCalculator.getScoreCount(feedBackListWeekly);
        List<ScoreCount> scoreCountMonthly = rankCalculator.getScoreCount(feedBackListMonthly);

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
        LocalDate today = LocalDate.now();
        LocalDate conditionDate = periodType.getStartOfThisPeriod(today);
        List<FoodRank> top10RankGapList = rankReader.getTop10ByRankGapDesc(periodType,
            conditionDate);
        List<FoodRank> topRankGapList = rankFilter.filterByRankGapGreaterThanOrEqualCondition(
            top10RankGapList);
        return rankImpl.getRankMenuResponseDtoList(topRankGapList);
    }


    public void calculateAndSaveRank(PeriodType periodType, LocalDate registerDate) {
        LocalDate preDate = periodType.getStartOfPreviousPeriod(registerDate);

        List<FeedBack> feedBackList = feedBackReader.getFeedBackInBoundary(preDate, registerDate);
        Map<Food, Double> foodScoreAverageMap = rankCalculator.calculateFoodScoreAverage(
            feedBackList);

        List<FoodRank> preRanking = rankReader.findByPeriodTypeAndStartPeriod(periodType, preDate);

        Map<Food, Integer> preFoodRanking = preRanking.stream()
            .collect(Collectors.toMap(
                FoodRank::getFood,
                FoodRank::getRanking
            ));

        Integer preRankingMedian = rankCalculator.getRankingMedian(preRanking);

        AtomicInteger rankCounter = new AtomicInteger(1);

        List<FoodRank> newFoodRankList = foodScoreAverageMap.entrySet().stream()
            .map(entry -> {
                Food food = entry.getKey();
                Double averageScore = entry.getValue();
                Integer preRank = preFoodRanking.getOrDefault(food, preRankingMedian);
                return buildRankForFood(food, averageScore,
                    rankCounter.getAndIncrement(), registerDate, preRank, periodType);
            })
            .collect(Collectors.toList());

        rankSaver.saveAll(newFoodRankList);
    }

    private FoodRank buildRankForFood(Food food, Double score, int currentRank, LocalDate thisWeek,
        Integer preRank, PeriodType periodType) {

        return FoodRank.builder()
            .food(food)
            .startPeriod(thisWeek)
            .score(score)
            .previousRanking(preRank)
            .periodType(periodType)
            .ranking(currentRank)
            .build();
    }

    public List<FoodRank> getRankList(LocalDate registerDate, PeriodType periodType) {
        return rankReader.findByPeriodTypeAndStartPeriod(periodType, registerDate);
    }

    public TrackingResponseDto getTrackingEvaluation(LocalDate localDate) {
        return feedBackTracker.getTrackingEvaluation(localDate);
    }
}
