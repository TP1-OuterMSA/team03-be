package com.example.SchoolLunchReport.statistics.impl;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse.ScoreCount;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.domain.type.Period;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class RankImpl {

    private final Comparator<Entry<Food, Double>> topComparator = Map.Entry.<Food, Double>comparingByValue()
        .reversed();
    private final Comparator<Entry<Food, Double>> downComparator = Map.Entry.comparingByValue();

    public RankMenuResponseDto generateRankResponses(
        List<FeedBack> feedBackList,
        int rankBoundary
    ) {
        Map<Food, Double> topRankMenus = extractRankMenu(feedBackList, rankBoundary,
            topComparator);
        List<RankMenuResponse> topRankResponses = mapToRankResponse(topRankMenus);

        Map<Food, Double> bottomRankMenus = extractRankMenu(feedBackList, rankBoundary,
            downComparator);
        List<RankMenuResponse> bottomRankResponses = mapToRankResponse(bottomRankMenus);

        return RankMenuResponseDto.of(topRankResponses, bottomRankResponses);
    }

    private List<RankMenuResponse> mapToRankResponse(Map<Food, Double> rankedMenus) {
        List<Map.Entry<Food, Double>> entries = rankedMenus.entrySet().stream()
            .toList();
        return IntStream.range(0, entries.size())
            .mapToObj(
                i -> RankMenuResponse.of(entries.get(i).getKey(), entries.get(i).getValue(),
                    i + 1))
            .toList();
    }

    private Map<Food, Double> extractRankMenu(
        List<FeedBack> feedBackList,
        int boundary,
        Comparator<Entry<Food, Double>> comparator
    ) {
        return feedBackList.stream()
            .collect(Collectors.groupingBy(
                FeedBack::getFood,
                Collectors.averagingDouble(FeedBack::getScore)
            ))
            .entrySet()
            .stream()
            .sorted(comparator)
            .limit(boundary)
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
//        if (value == null) {
//            return null;
//        }
//        return BigDecimal.valueOf(value)
//            .setScale(1, RoundingMode.HALF_UP)
//            .doubleValue();
    }

    public StatisticsResponse produceStatistics(List<FeedBack> feedBackList, Period period) {
        Map<Integer, Long> scoreCount = feedBackList.stream()
            .collect(Collectors.groupingBy(
                FeedBack::getScore,
                Collectors.counting()
            ));
        List<ScoreCount> scores = IntStream.rangeClosed(1, 5)
            .mapToObj(score -> new ScoreCount(score, scoreCount.getOrDefault(score, 0L)))
            .toList();
        return StatisticsResponse.builder()
            .period(period)
            .scores(scores)
            .build();
    }
}
