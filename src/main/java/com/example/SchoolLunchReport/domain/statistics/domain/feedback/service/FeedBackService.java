package com.example.SchoolLunchReport.domain.statistics.domain.feedback.service;

import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.TrackingResponseDto;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.CategoryScoreAvgDto;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.support.FeedBackReader;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.support.FeedBackTracker;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FeedBackService {

    final FeedBackReader feedBackReader;
    final FeedBackTracker feedBackTracker;

    public List<FeedBack> getFeedBackInBoundary(Boundary weeklyBoundary) {
        return feedBackReader.getFeedBackInBoundary(weeklyBoundary);
    }

    public List<FeedBack> getFeedBackByMenu(List<Menu> menuInBoundary) {
        return feedBackReader.getFeedBackByMenu(menuInBoundary);
    }

    public TrackingResponseDto getTrackingEvaluation(LocalDate localDate) {
        return feedBackTracker.getTrackingEvaluation(localDate);
    }

    public List<CategoryScoreAvgDto> getCategoryScore(LocalDate startDate, LocalDate endDate) {
        return feedBackReader.getCategoryScore(startDate, endDate);
    }
}
