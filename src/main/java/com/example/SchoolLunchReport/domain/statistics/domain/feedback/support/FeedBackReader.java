package com.example.SchoolLunchReport.domain.statistics.domain.feedback.support;

import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.FeedBackJpaRepo;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.dto.CategoryScoreAvgDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedBackReader {

    final FeedBackJpaRepo feedBackJpaRepo;

    public List<FeedBack> getFeedBackInBoundary(Boundary boundary) {
        return feedBackJpaRepo.findByCreatedAtBetween(boundary.startDate(), boundary.endDate());
    }

    public List<FeedBack> getFeedBackInBoundary(LocalDate startDate) {
        return feedBackJpaRepo.findByCreatedAt(startDate);
    }

    public List<FeedBack> getFeedBackInBoundary(LocalDate startDate, LocalDate endDate) {

        return feedBackJpaRepo.findByCreatedAtBetween(startDate, endDate);
    }

    public List<CategoryScoreAvgDto> getCategoryScore(LocalDate startDate, LocalDate endDate) {
        List<CategoryScoreAvgDto> categoryScoreAvgDtos = feedBackJpaRepo.getAvgScoreByCategory(
            startDate, endDate);
        return categoryScoreAvgDtos.stream()
            .map(dto -> new CategoryScoreAvgDto(
                dto.category(),
                Math.round(dto.average() * 100) / 100.0
            ))
            .toList();
    }

    public List<FeedBack> getFeedBackByMenu(List<Menu> menuInBoundary) {
        return feedBackJpaRepo.getFeedBackByMenu(menuInBoundary);
    }
}
