package com.example.SchoolLunchReport.statistics.rank.impl;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.rank.controller.dto.RankMenuResponse;
import com.example.SchoolLunchReport.statistics.rank.controller.dto.RankMenuResponseDto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
        return BigDecimal.valueOf(value)
            .setScale(1, RoundingMode.HALF_UP)
            .doubleValue();
    }

}
