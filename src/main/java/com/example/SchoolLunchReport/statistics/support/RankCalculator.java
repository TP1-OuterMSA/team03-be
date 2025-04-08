package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import org.springframework.stereotype.Component;

@Component
public class RankCalculator {

    private final Comparator<Entry<Food, Double>> topComparator = Map.Entry.<Food, Double>comparingByValue()
        .reversed();


    public List<ScoreCount> getScoreCount(
        List<FeedBack> feedBackList
    ) {
        Map<Double, Long> scoreCount = feedBackList.stream()
            .collect(Collectors.groupingBy(
                FeedBack::getScore,
                Collectors.counting()
            ));

        List<ScoreCount> scores =
            DoubleStream.iterate(1.0, d -> d <= 5.0, d -> d + 0.5)
                .mapToObj(score -> new ScoreCount(score, scoreCount.getOrDefault(score, 0L)))
                .toList();
        return scores;
    }

    public Map<Food, Double> calculateFoodScoreAverage(List<FeedBack> feedBackList) {
        return feedBackList.stream()
            .collect(Collectors.groupingBy(
                FeedBack::getFood,
                Collectors.averagingDouble(FeedBack::getScore)
            ))
            .entrySet()
            .stream()
            .sorted(topComparator)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> roundToOneDecimal(entry.getValue()),
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    private Double roundToOneDecimal(Double value) {
        if (value == null) {
            return null;
        }
        return Math.round(value * 10.0) / 10.0;
    }


    public Integer getRankingMedian(
        List<FoodRank> preRanking
    ) {
        List<Integer> rankings = preRanking.stream()
            .map(FoodRank::getRanking)
            .sorted()
            .toList();
        return calculateMedian(rankings);
    }

    private static Integer calculateMedian(List<Integer> rankings) {
        int size = rankings.size();
        if (size == 0) {
            return null;
//            throw new IllegalArgumentException("해당 기간의 FoodRank 데이터가 없습니다.");
        }
        if (size % 2 == 1) {
            // 홀수개인 경우, 가운데 값 반환
            return rankings.get(size / 2);
        } else {
            return (rankings.get(size / 2 - 1) + rankings.get(size / 2)) / 2;
        }
    }
}
