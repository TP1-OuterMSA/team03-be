package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
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

}
