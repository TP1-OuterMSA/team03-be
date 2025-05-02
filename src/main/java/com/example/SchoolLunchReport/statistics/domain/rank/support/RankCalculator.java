package com.example.SchoolLunchReport.statistics.domain.rank.support;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
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
        Map<Food, Integer> preRanking
    ) {
        List<Integer> rankings = preRanking.values().stream()
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
            return rankings.get(size / 2);
        } else {
            return (rankings.get(size / 2 - 1) + rankings.get(size / 2)) / 2;
        }
    }

    public Double getScoreAverage(List<FeedBack> feedBackList) {
        double average = feedBackList.stream()
            .mapToDouble(FeedBack::getScore)
            .average()
            .orElse(0.0);

        return Math.round(average * 100) / 100.0;
    }

    public void compareRank(List<FoodRank> foodRankList, Map<Food, Integer> preRankList,
        Integer preRankingMedian) {
        for (FoodRank foodRank : foodRankList) {
            foodRank.setPreviousRanking(
                preRankList.getOrDefault(foodRank.getFood(), preRankingMedian));
        }
    }
}
