package com.example.SchoolLunchReport.statistics.impl;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.entity.Rank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RankScheduler {

    private final FeedBackImpl feedBackImpl;
    private final RankImpl rankImpl;
    private final Comparator<Entry<Food, Double>> topComparator = Map.Entry.<Food, Double>comparingByValue()
        .reversed();

    @Transactional
    @Scheduled(cron = "0 0 23 ? * SUN", zone = "Asia/Seoul")
    public void calculateAndSaveWeeklyFoodRank() {

        LocalDate thisWeek = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate lastWeek = thisWeek.minusWeeks(1);
        PeriodType periodType = PeriodType.WEEKLY;

        calculateAndSaveRank(periodType, thisWeek, lastWeek);

    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")
    public void calculateAndSaveMonthlyFoodRank() {

        LocalDate thisMonth = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate lastMonth = thisMonth.minusMonths(1);
        PeriodType periodType = PeriodType.MONTHLY;

        calculateAndSaveRank(periodType, thisMonth, lastMonth);

    }

    private void calculateAndSaveRank(PeriodType periodType, LocalDate thisMonth,
        LocalDate lastMonth) {
        List<FeedBack> feedBackList = feedBackImpl.getFeedBackBoundary(periodType);

        Map<Food, Double> foodScoreAverageMap = calculateFoodScoreAverage(feedBackList,
            topComparator);

        // 과거의 rank를 가져와야 해.
        AtomicInteger rankCounter = new AtomicInteger(1);

        List<Rank> newRankList = foodScoreAverageMap.entrySet().stream()
            .map(entry -> buildRankForFood(entry.getKey(), entry.getValue(),
                rankCounter.getAndIncrement(), thisMonth, lastMonth, periodType))
            .collect(Collectors.toList());

        rankImpl.saveAll(newRankList);
    }

    private Map<Food, Double> calculateFoodScoreAverage(
        List<FeedBack> feedBackList,
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
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> roundToOneDecimal(entry.getValue()),
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    private Rank buildRankForFood(Food food, Double score, int currentRank, LocalDate thisWeek,
        LocalDate lastWeek, PeriodType periodType) {
        Rank previousRank = rankImpl.findByFoodAndPeriodTypeAndStartPeriod(food, periodType,
            lastWeek);

        return Rank.builder()
            .food(food)
            .startPeriod(thisWeek)
            .score(score)
            .previousRank(previousRank != null ? previousRank.getRank() : null)
            .periodType(PeriodType.WEEKLY)
            .rank(currentRank)
            .build();
    }

    private Double roundToOneDecimal(Double value) {
        if (value == null) {
            return null;
        }
        return Math.round(value * 10.0) / 10.0;
    }
}
