package com.example.SchoolLunchReport.statistics.domain.feedback.repo;

import com.example.SchoolLunchReport.statistics.domain.feedback.entity.CategoryScoreSumDto;
import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepositoryCustom {

    List<CategoryScoreSumDto> sumScoreByCategoryBetween(LocalDate startDate, LocalDate endDate);

}
