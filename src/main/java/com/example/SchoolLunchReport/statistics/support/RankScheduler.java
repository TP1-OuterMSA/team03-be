package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.service.StatisticsService;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RankScheduler {

    private final StatisticsService statisticsService;


    @Transactional
    @Scheduled(cron = "0 0 23 ? * SUN", zone = "Asia/Seoul")
    public void calculateAndSaveWeeklyFoodRank() {

        LocalDate thisWeek = LocalDate.now(ZoneId.of("Asia/Seoul"));
        PeriodType periodType = PeriodType.WEEKLY;
        statisticsService.calculateAndSaveRank(periodType, thisWeek);

    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul")
    public void calculateAndSaveMonthlyFoodRank() {

        LocalDate thisMonth = LocalDate.now(ZoneId.of("Asia/Seoul"));
        PeriodType periodType = PeriodType.MONTHLY;
        LocalDate preMonth = periodType.getLastOfThisPeriod(thisMonth);
        statisticsService.calculateAndSaveRank(periodType, thisMonth);

    }

}
