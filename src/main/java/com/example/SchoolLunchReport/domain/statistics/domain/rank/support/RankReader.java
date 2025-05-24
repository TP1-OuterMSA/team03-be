package com.example.SchoolLunchReport.domain.statistics.domain.rank.support;

import static com.example.SchoolLunchReport.domain.statistics.domain.type.RankType.TOP;

import com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType;
import com.example.SchoolLunchReport.domain.statistics.domain.rank.entity.FoodRank;
import com.example.SchoolLunchReport.domain.statistics.domain.rank.repo.RankJpaRepo;
import com.example.SchoolLunchReport.domain.statistics.domain.type.RankType;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankReader {

    private final RankJpaRepo rankJpaRepo;

    public List<FoodRank> getFoodRank(
        PeriodType periodType,
        LocalDate registerDate,
        RankType rankType
    ) {
        if (rankType == TOP) {
            return rankJpaRepo.findTop5ByPeriodTypeAndRegisterDateOrderByRankingAsc(
                periodType,
                registerDate
            );
        } else {
            return rankJpaRepo.findTop5ByPeriodTypeAndRegisterDateOrderByRankingDesc
                (
                    periodType,
                    registerDate
                );
        }
    }

    public List<FoodRank> getTop10ByRankGapDesc(PeriodType periodType, LocalDate registerDate) {
        return rankJpaRepo.findTop5ByRankGapDesc(
            periodType,
            registerDate,
            PageRequest.of(0, 10));
    }

    public List<FoodRank> findByPeriodTypeAndRegisterDate(PeriodType periodType,
        LocalDate registerDate) {
        return rankJpaRepo.findByPeriodTypeAndRegisterDate(
            periodType, registerDate);
    }
}
