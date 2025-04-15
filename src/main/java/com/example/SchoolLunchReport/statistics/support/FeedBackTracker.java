package com.example.SchoolLunchReport.statistics.support;

import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.MONTHLY;
import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.WEEKLY;

import com.example.SchoolLunchReport.statistics.controller.dto.response.response.TrackingResponseDto;
import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedBackTracker {

    private final FeedBackReader feedBackReader;
    private final RankCalculator rankCalculator;

    private static final int WEEK_DAYS_COUNT = 5;
    private static final int MONTH_COUNT = 5;

    public TrackingResponseDto getTrackingEvaluation(LocalDate localDate) {
        List<Double> weeklyScore = calculateWeeklyScores(localDate);
        List<Double> monthlyScore = calculateMonthlyScores(localDate);

        return TrackingResponseDto.builder()
            .weeklyScore(weeklyScore)
            .monthlyScore(monthlyScore)
            .build();
    }

    private List<Double> calculateWeeklyScores(LocalDate date) {
        LocalDate startOfPreviousPeriod = WEEKLY.getStartOfPreviousPeriod(date);
        LocalDate monday = startOfPreviousPeriod.with(
            TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        return getScoresForPeriod(monday, WEEK_DAYS_COUNT, ChronoUnit.DAYS);
    }

    private List<Double> calculateMonthlyScores(LocalDate date) {
        LocalDate thisMonth = MONTHLY.getStartOfThisPeriod(date);
        LocalDate startMonth = thisMonth.minusMonths(MONTH_COUNT);
        return getScoresForPeriod(startMonth, MONTH_COUNT, ChronoUnit.MONTHS);
    }

    private List<Double> getScoresForPeriod(LocalDate startDate, int count, ChronoUnit unit) {
        if (unit == ChronoUnit.DAYS) {
            return getScoresInDay(startDate, count, unit);
        } else if (unit == ChronoUnit.MONTHS) {
            return getScoresInBoundary(startDate, count, unit);
        }
        throw new RuntimeException("없는 주기 단위 입니다.");
    }

    private List<Double> getScoresInBoundary(LocalDate startDate, int count, ChronoUnit unit) {
        List<Double> scores = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            LocalDate targetDate = startDate.plus(i, unit);
            LocalDate endOfMonth = YearMonth.from(targetDate).atEndOfMonth();
            List<FeedBack> feedBackList = feedBackReader.getFeedBackInBoundary(targetDate,
                endOfMonth);
            Double scoreAverage = rankCalculator.getScoreAverage(feedBackList);
            scores.add(scoreAverage);
        }
        return scores;
    }

    private List<Double> getScoresInDay(LocalDate startDate, int count, ChronoUnit unit) {
        List<Double> scores = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            LocalDate targetDate = startDate.plus(i, unit);
            List<FeedBack> feedBackList = feedBackReader.getFeedBackInBoundary(targetDate);
            Double scoreAverage = rankCalculator.getScoreAverage(feedBackList);
            scores.add(scoreAverage);
        }
        return scores;
    }
}
