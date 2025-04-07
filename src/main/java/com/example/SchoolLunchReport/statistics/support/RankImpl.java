package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.type.RankType;
import com.example.SchoolLunchReport.statistics.repository.RankJpaRepo;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankImpl {

    private final RankJpaRepo rankJpaRepo;

    public List<RankMenuResponseDto> generateRankResponse(
        PeriodType periodType,
        LocalDate conditionDate,
        RankType rankType
    ) {
        List<FoodRank> foodRankList;
        if (rankType.equals(RankType.TOP)) {
            foodRankList = rankJpaRepo.findTop5ByPeriodTypeAndStartPeriodOrderByRankingAsc(
                periodType,
                conditionDate
            );
        } else {
            foodRankList = rankJpaRepo.findTop5ByPeriodTypeAndStartPeriodOrderByRankingDesc(
                periodType,
                conditionDate
            );
        }

        return getRankMenuResponseDtoList(foodRankList);
    }

    private static List<RankMenuResponseDto> getRankMenuResponseDtoList(
        List<FoodRank> topFoodRanks) {
        return IntStream.range(0, topFoodRanks.size())
            .mapToObj(i -> RankMenuResponseDto.of(topFoodRanks.get(i), i + 1))
            .toList();
    }

    public StatisticsResponse produceStatistics(
        List<FeedBack> feedBackList,
        PeriodType periodType
    ) {
        Map<Integer, Long> scoreCount = feedBackList.stream()
            .collect(Collectors.groupingBy(
                FeedBack::getScore,
                Collectors.counting()
            ));
        List<ScoreCount> scores = IntStream.rangeClosed(1, 5)
            .mapToObj(score -> new ScoreCount(score, scoreCount.getOrDefault(score, 0L)))
            .toList();
        return StatisticsResponse.builder()
            .periodType(periodType)
            .scores(scores)
            .build();
    }

    public List<RankMenuResponseDto> getTrendingMenu(LocalDate conditionDate,
        PeriodType periodType) {
        List<FoodRank> foodRankList = rankJpaRepo.findTop5ByRankGapDesc(
            periodType,
            conditionDate,
            PageRequest.of(0, 5));
        return getRankMenuResponseDtoList(foodRankList);
    }

    public FoodRank findByFoodAndPeriodTypeAndStartPeriod(Food food, PeriodType periodType,
        LocalDate lastWeek) {
        return rankJpaRepo.findByFoodAndPeriodTypeAndStartPeriod(
            food, periodType, lastWeek);
    }

    public void saveAll(List<FoodRank> newFoodRankList) {
        rankJpaRepo.saveAll(newFoodRankList);
    }
}
