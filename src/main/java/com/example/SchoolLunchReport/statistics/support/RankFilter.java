package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
import java.util.List;
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
}
