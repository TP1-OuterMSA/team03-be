package com.example.SchoolLunchReport.statistics.rank.service;

import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.feedback.impl.FeedBackImpl;
import com.example.SchoolLunchReport.statistics.rank.controller.dto.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.rank.domain.type.Period;
import com.example.SchoolLunchReport.statistics.rank.impl.RankImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RankService {

    final FeedBackImpl feedBackImpl;
    final RankImpl rankImpl;

    public RankMenuResponseDto getRankMenu(Period period) {
        List<FeedBack> feedBackList = feedBackImpl.getFeedBackBoundary(period);

        return rankImpl.generateRankResponses(feedBackList, 5);
    }


}
