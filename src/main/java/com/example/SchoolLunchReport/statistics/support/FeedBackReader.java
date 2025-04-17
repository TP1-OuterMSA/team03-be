package com.example.SchoolLunchReport.statistics.support;

import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.MONTHLY;
import static com.example.SchoolLunchReport.statistics.domain.type.PeriodType.WEEKLY;

import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.repository.FeedBackJpaRepo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedBackReader {

    final FeedBackJpaRepo feedBackJpaRepo;

    public List<FeedBack> getFeedBackInBoundary(LocalDate startDate, LocalDate endDate) {
//        return feedBackJpaRepo.findByCreatedAtBetween(startDate, endDate.minusDays(1));
        return feedBackJpaRepo.findByCreatedAtBetween(startDate, endDate);
    }


    public List<FeedBack> getFeedBackInBoundary(LocalDate startDate) {
        return feedBackJpaRepo.findByCreatedAt(startDate);
    }

    public List<FeedBack> getFeedBackInBoundary(LocalDate date, PeriodType periodType) {

        LocalDate startDate;
        LocalDate endDate;

        if (periodType.equals(WEEKLY)) {
            startDate = WEEKLY.getStartOfPreviousPeriod(date);
            endDate = WEEKLY.getStartOfThisPeriod(date);
        } else {
            startDate = MONTHLY.getStartOfPreviousPeriod(date);
            endDate = MONTHLY.getStartOfThisPeriod(date);
        }

        return getFeedBackInBoundary(startDate, endDate);
    }
}
