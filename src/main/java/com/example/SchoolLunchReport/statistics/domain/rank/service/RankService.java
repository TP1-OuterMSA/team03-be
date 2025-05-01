package com.example.SchoolLunchReport.statistics.domain.rank.service;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.domain.boundary.support.BoundaryMapper;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.rank.support.RankCalculator;
import com.example.SchoolLunchReport.statistics.domain.rank.support.RankFilter;
import com.example.SchoolLunchReport.statistics.domain.rank.support.RankImpl;
import com.example.SchoolLunchReport.statistics.domain.rank.support.RankReader;
import com.example.SchoolLunchReport.statistics.domain.rank.support.RankSaver;
import com.example.SchoolLunchReport.statistics.domain.type.RankType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    final BoundaryMapper boundaryMapper;

    public List<RankMenuResponseDto> getTopRankMenu(PeriodType periodType, LocalDate date) {
        LocalDate rankRegisterDate = boundaryMapper.getRankRegisterDate(periodType, date);
        List<FoodRank> top5FoodRank = rankReader.getFoodRank(periodType, rankRegisterDate,
            RankType.TOP);
        return rankImpl.getRankMenuResponseDtoList(top5FoodRank);
    }

    public List<RankMenuResponseDto> getBottomRankMenu(PeriodType periodType, LocalDate date) {
        LocalDate rankRegisterDate = boundaryMapper.getRankRegisterDate(periodType, date);
        List<FoodRank> bottom5FoodRank = rankReader.getFoodRank(periodType, rankRegisterDate,
            RankType.BOTTOM);
        return rankImpl.getRankMenuResponseDtoList(
            bottom5FoodRank);
    }

    public List<ScoreCount> getScoreCount(List<FeedBack> feedBackListWeekly) {
        return rankCalculator.getScoreCount(feedBackListWeekly);
    }

    public List<RankMenuResponseDto> getTrendingMenu(PeriodType periodType) {
        LocalDate today = LocalDate.now();
        LocalDate conditionDate = boundaryMapper.getRankRegisterDate(periodType, today);
        List<FoodRank> top10RankGapList = rankReader.getTop10ByRankGapDesc(periodType,
            conditionDate);
        List<FoodRank> topRankGapList = rankFilter.filterByRankGapGreaterThanOrEqualCondition(
            top10RankGapList);
        return rankImpl.getRankMenuResponseDtoList(topRankGapList);
    }

    public void calculateAndSave(
        List<FeedBack> feedBackList,
        PeriodType periodType,
        LocalDate registerDate) {

        Map<Food, Double> foodScoreAverageMap = rankCalculator.calculateFoodScoreAverage(
            feedBackList);

        List<FoodRank> foodRankList = rankImpl.generateRanks(foodScoreAverageMap, periodType,
            registerDate);
        List<FoodRank> preFoodRankList = rankReader.findByPeriodTypeAndRegisterDate(
            periodType, registerDate);
        Map<Food, Integer> preFoodRanking = rankImpl.getRankingMap(preFoodRankList);

        Integer preRankingMedian = rankCalculator.getRankingMedian(preFoodRanking);

        rankCalculator.compareRank(foodRankList, preFoodRanking, preRankingMedian);

        rankSaver.saveAll(foodRankList);
    }

    public List<FoodRank> getRankList(PeriodType periodType, LocalDate registerDate) {
        return rankReader.findByPeriodTypeAndRegisterDate(periodType, registerDate);
    }
}
