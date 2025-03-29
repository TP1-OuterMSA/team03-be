package com.example.SchoolLunchReport.statistics.feedback.impl;

import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.feedback.repo.FeedBackJpaRepo;
import com.example.SchoolLunchReport.statistics.rank.domain.type.Period;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedBackImpl {

    final FeedBackJpaRepo feedBackJpaRepo;

    public List<FeedBack> getFeedBackBoundary(Period period) {
        LocalDate conditionDate = Period.getConditionDate(period);
        return feedBackJpaRepo.findByCreatedAtAfter(conditionDate);
    }
}
