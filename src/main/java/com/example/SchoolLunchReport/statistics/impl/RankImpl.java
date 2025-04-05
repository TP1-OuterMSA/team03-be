package com.example.SchoolLunchReport.statistics.impl;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.domain.entity.Rank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.type.RankType;
import com.example.SchoolLunchReport.statistics.repository.RankJpaRepo;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
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
        List<Rank> rankList;
        if (rankType.equals(RankType.TOP)) {
            rankList = rankJpaRepo.findTop5ByPeriodTypeAndStartPeriodOrderByRankAsc(
                periodType,
                conditionDate);
        } else {
            rankList = rankJpaRepo.findTop5ByPeriodTypeAndStartPeriodOrderByRankDesc(
                periodType,
                conditionDate);
        }

        return getRankMenuResponseDtoList(rankList);
    }

    private static List<RankMenuResponseDto> getRankMenuResponseDtoList(List<Rank> topRanks) {
        return topRanks.stream()
            .map(RankMenuResponseDto::from)
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

    public RankMenuResponseDto getTrendingMenu(List<FeedBack> feedBackList, PeriodType periodType) {

        return null;
    }

    public Rank findByFoodAndPeriodTypeAndStartPeriod(Food food, PeriodType periodType,
        LocalDate lastWeek) {
        return rankJpaRepo.findByFoodAndPeriodTypeAndStartPeriod(
            food, periodType, lastWeek);
    }

    public void saveAll(List<Rank> newRankList) {
        rankJpaRepo.saveAll(newRankList);
    }
}
