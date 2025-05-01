package com.example.SchoolLunchReport.statistics.domain.rank.support;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankImpl {

    public List<RankMenuResponseDto> getRankMenuResponseDtoList(
        List<FoodRank> topFoodRanks) {
        return IntStream.range(0, topFoodRanks.size())
            .mapToObj(i -> RankMenuResponseDto.of(topFoodRanks.get(i), i + 1))
            .toList();
    }


    public List<FoodRank> generateRanks(Map<Food, Double> foodScoreAverageMap,
        PeriodType periodType, LocalDate registerDate) {
        AtomicInteger rankCounter = new AtomicInteger(1);
        return foodScoreAverageMap.entrySet().stream()
            .map(entry -> {
                Food food = entry.getKey();
                Double averageScore = entry.getValue();
                return FoodRank.builder()
                    .food(food)
                    .ranking(rankCounter.get())
                    .score(averageScore)
                    .registerDate(registerDate)
                    .periodType(periodType)
                    .build();
            })
            .toList();
    }

    public Map<Food, Integer> getRankingMap(List<FoodRank> foodRankList) {
        return foodRankList.stream()
            .collect(Collectors.toMap(
                FoodRank::getFood,
                FoodRank::getRanking
            ));
    }
}
