package com.example.SchoolLunchReport.statistics.service;

import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.MONTHLY;
import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.WEEKLY;

import com.example.SchoolLunchReport.statistics.controller.dto.response.CombinedRankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.CombinedStatisticsResponse;
import com.example.SchoolLunchReport.statistics.controller.dto.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.controller.dto.response.StatisticsResponse;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.domain.type.RankType;
import com.example.SchoolLunchReport.statistics.support.FeedBackImpl;
import com.example.SchoolLunchReport.statistics.support.RankImpl;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    final FeedBackImpl feedBackImpl;
    final RankImpl rankImpl;

    public CombinedRankMenuResponseDto getRankMenu(PeriodType periodType, LocalDate date) {

        LocalDate targetDate = periodType.getStartOfThisPeriod(date);

        List<RankMenuResponseDto> topRankMenuResponseDtos = rankImpl.generateRankResponse(
            periodType,
            targetDate,
            RankType.TOP
        );
        List<RankMenuResponseDto> bottomRankMenuResponseDtos = rankImpl.generateRankResponse(
            periodType,
            targetDate,
            RankType.BOTTOM
        );
        return CombinedRankMenuResponseDto.of(topRankMenuResponseDtos, bottomRankMenuResponseDtos);
    }


    public CombinedStatisticsResponse getStatistics(LocalDate date) {

        LocalDate startWeekDate = WEEKLY.getStartOfThisPeriod(date);
        LocalDate endWeekDate = WEEKLY.getLastOfThisPeriod(date);
        List<FeedBack> feedBackListWeekly = feedBackImpl.getFeedBackInBoundary(endWeekDate,
            startWeekDate);

        LocalDate startMonthDate = MONTHLY.getLastOfThisPeriod(date);
        LocalDate endMonthDate = MONTHLY.getStartOfThisPeriod(date);

        List<FeedBack> feedBackListMonthly = feedBackImpl.getFeedBackInBoundary(startMonthDate,
            endMonthDate);

        StatisticsResponse statisticsResponseWeekly = rankImpl.produceStatistics(feedBackListWeekly,
            WEEKLY);
        StatisticsResponse statisticsResponseMonthly = rankImpl.produceStatistics(
            feedBackListMonthly,
            MONTHLY);
        return new CombinedStatisticsResponse(statisticsResponseWeekly, statisticsResponseMonthly);
    }

    public List<RankMenuResponseDto> getTrendingMenu(PeriodType periodType) {
        LocalDate today = LocalDate.now();
        LocalDate conditionDate = periodType.getStartOfThisPeriod(today);
        return rankImpl.getTrendingMenu(conditionDate, periodType);
    }
}
