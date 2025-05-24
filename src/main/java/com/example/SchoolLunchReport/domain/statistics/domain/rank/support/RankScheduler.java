package com.example.SchoolLunchReport.domain.statistics.domain.rank.support;

import com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.domain.statistics.service.StatisticsFacade;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RankScheduler {

    private final StatisticsFacade statisticsFacade;

    @Transactional
    @Scheduled(cron = "0 0 23 ? * SUN", zone = "Asia/Seoul")
    public void calculateAndSaveWeeklyFoodRank() {

        LocalDate thisSunDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        PeriodType periodType = PeriodType.WEEKLY;

        statisticsFacade.calculateAndSaveRank(periodType, thisSunDate);

    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")
    public void calculateAndSaveMonthlyFoodRank() {
        LocalDate thisMonth = LocalDate.now(ZoneId.of("Asia/Seoul"));
        PeriodType periodType = PeriodType.MONTHLY;
        statisticsFacade.calculateAndSaveRank(periodType, thisMonth);
    }

}
