package com.example.SchoolLunchReport.statistics.domain.rank.service;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.type.RankType;
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

@Service
@RequiredArgsConstructor
public class RankService {

    final RankImpl rankImpl;
    final RankReader rankReader;
    final RankCalculator rankCalculator;
    final RankSaver rankSaver;
    final RankFilter rankFilter;

    public List<RankMenuResponseDto> getTopRankMenu(PeriodType periodType, LocalDate date) {
        LocalDate targetDate = periodType.getStartOfThisPeriod(date);
        List<FoodRank> top5FoodRank = rankReader.getFoodRank(periodType, targetDate, RankType.TOP);
        return rankImpl.getRankMenuResponseDtoList(top5FoodRank);
    }

    public List<RankMenuResponseDto> getBottomRankMenu(PeriodType periodType, LocalDate date) {
        LocalDate targetDate = periodType.getStartOfThisPeriod(date);
        List<FoodRank> bottom5FoodRank = rankReader.getFoodRank(periodType, targetDate,
            RankType.BOTTOM);
        return rankImpl.getRankMenuResponseDtoList(
            bottom5FoodRank);
    }

    public List<ScoreCount> getScoreCount(List<FeedBack> feedBackListWeekly) {
        return rankCalculator.getScoreCount(feedBackListWeekly);
    }

    public List<RankMenuResponseDto> getTrendingMenu(PeriodType periodType) {
        LocalDate today = LocalDate.now();
        LocalDate conditionDate = periodType.getStartOfThisPeriod(today);
        List<FoodRank> top10RankGapList = rankReader.getTop10ByRankGapDesc(periodType,
            conditionDate);
        List<FoodRank> topRankGapList = rankFilter.filterByRankGapGreaterThanOrEqualCondition(
            top10RankGapList);
        return rankImpl.getRankMenuResponseDtoList(topRankGapList);
    }

    public void calculateAndSave(
        List<FeedBack> feedBackList,
        PeriodType periodType,
        LocalDate preDate,
        LocalDate registerDate) {
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

    public List<FoodRank> getRankList(PeriodType periodType, LocalDate registerDate) {
        return rankReader.findByPeriodTypeAndStartPeriod(periodType, registerDate);
    }
}
