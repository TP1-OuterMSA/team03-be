package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RankFilter {

    private static final Integer TREND_CONDITION = 3;

    public List<FoodRank> filterByRankGapGreaterThanOrEqualCondition(List<FoodRank> foodRankList) {
        return foodRankList.stream()
            .filter(foodRank -> foodRank.getRankGap() >= TREND_CONDITION)
            .limit(3)
            .collect(Collectors.toList());
    }

    public List<Entry<String, Integer>> filterTopN(HashMap<String, Integer> desiredFoodMap, int limit) {

        return desiredFoodMap.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
            .limit(limit)
            .toList();
    }
}
