package com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo;

import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.CategoryScoreAvgDto;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepositoryCustom {

    List<CategoryScoreAvgDto> sumScoreByCategoryBetween(LocalDate startDate, LocalDate endDate);

    List<FeedBack> getFeedBackByMenu(List<Menu> menuList);
}
