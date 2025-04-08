package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.repository.RankJpaRepo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankReader {

    private final RankJpaRepo rankJpaRepo;

    public List<FoodRank> getTop5FoodRank(PeriodType periodType, LocalDate startPeriod) {
        return rankJpaRepo.findTop5ByPeriodTypeAndStartPeriodOrderByRankingAsc(
            periodType,
            startPeriod
        );
    }

    public List<FoodRank> getBottom5FoodRank(PeriodType periodType, LocalDate startPeriod) {
        return rankJpaRepo.findTop5ByPeriodTypeAndStartPeriodOrderByRankingDesc
            (
                periodType,
                startPeriod
            );
    }

    public List<FoodRank> getTop10ByRankGapDesc(PeriodType periodType, LocalDate startPeriod) {
        return rankJpaRepo.findTop5ByRankGapDesc(
            periodType,
            startPeriod,
            PageRequest.of(0, 10));
    }

    public List<FoodRank> findByPeriodTypeAndStartPeriod(PeriodType periodType,
        LocalDate startPeriod) {
        return rankJpaRepo.findByPeriodTypeAndStartPeriod(periodType, startPeriod);
    }
}
