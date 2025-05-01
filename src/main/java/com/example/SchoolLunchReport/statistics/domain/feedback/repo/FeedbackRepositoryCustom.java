package com.example.SchoolLunchReport.statistics.domain.feedback.repo;

import com.example.SchoolLunchReport.statistics.domain.feedback.entity.CategoryScoreAvgDto;
import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepositoryCustom {

    List<CategoryScoreAvgDto> sumScoreByCategoryBetween(LocalDate startDate, LocalDate endDate);

}
