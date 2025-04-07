package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RankScheduler {

    private final FeedBackImpl feedBackImpl;
    private final RankImpl rankImpl;
    private final Comparator<Entry<Food, Double>> topComparator = Map.Entry.<Food, Double>comparingByValue()
        .reversed();

    @Transactional
    @Scheduled(cron = "0 0 23 ? * SUN", zone = "Asia/Seoul")
    public void calculateAndSaveWeeklyFoodRank() {

        LocalDate thisWeek = LocalDate.now(ZoneId.of("Asia/Seoul"));
        PeriodType periodType = PeriodType.WEEKLY;
        LocalDate preWeek = periodType.getLastOfThisPeriod(thisWeek);
        calculateAndSaveRank(periodType, thisWeek, preWeek);

    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")
    public void calculateAndSaveMonthlyFoodRank() {

        LocalDate thisMonth = LocalDate.now(ZoneId.of("Asia/Seoul"));
        PeriodType periodType = PeriodType.MONTHLY;
        LocalDate preMonth = periodType.getLastOfThisPeriod(thisMonth);

        calculateAndSaveRank(periodType, thisMonth, preMonth);

    }

    public void calculateAndSaveRank(PeriodType periodType, LocalDate registerDate,
        LocalDate preDate) {
        log.info("===== 랭크 계산 등록일: {} / 타입: {} =====", preDate, registerDate, periodType);

        // conditionDate는 4월6일 일요일에 이게 작동되면 3월31일 월요일인거임. 그 이후 피드백리스트를 가져온거고
        // 아 그래도 3월31일에 작성된 피드백을 가져와야 하니까 맞나?

        List<FeedBack> feedBackList = feedBackImpl.getFeedBackInBoundary(preDate, registerDate);

        Map<Food, Double> foodScoreAverageMap = calculateFoodScoreAverage(feedBackList,
            topComparator);

        // 과거의 rank를 가져와야 해.
        AtomicInteger rankCounter = new AtomicInteger(1);

        List<FoodRank> newFoodRankList = foodScoreAverageMap.entrySet().stream()
            .map(entry -> buildRankForFood(entry.getKey(), entry.getValue(),
                rankCounter.getAndIncrement(), registerDate, preDate, periodType))
            .collect(Collectors.toList());
        log.info("신규 랭크 저장 수: {}", newFoodRankList.size());

        rankImpl.saveAll(newFoodRankList);
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

    private FoodRank buildRankForFood(Food food, Double score, int currentRank, LocalDate thisWeek,
        LocalDate lastWeek, PeriodType periodType) {
        FoodRank previousFoodRank = rankImpl.findByFoodAndPeriodTypeAndStartPeriod(food, periodType,
            lastWeek);

        return FoodRank.builder()
            .food(food)
            .startPeriod(thisWeek)
            .score(score)
            .previousRanking(previousFoodRank != null ? previousFoodRank.getRanking() : 0)
            .periodType(periodType)
            .ranking(currentRank)
            .build();
    }

    private Double roundToOneDecimal(Double value) {
        if (value == null) {
            return null;
        }
        return Math.round(value * 10.0) / 10.0;
    }
}
