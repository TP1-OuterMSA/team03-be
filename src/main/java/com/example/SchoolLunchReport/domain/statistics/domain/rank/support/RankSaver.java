package com.example.SchoolLunchReport.domain.statistics.domain.rank.support;

import com.example.SchoolLunchReport.domain.statistics.domain.rank.entity.FoodRank;
import com.example.SchoolLunchReport.domain.statistics.domain.rank.repo.RankJpaRepo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankSaver {

    final RankJpaRepo rankJpaRepo;

    public void saveAll(List<FoodRank> newFoodRankList) {
        rankJpaRepo.saveAll(newFoodRankList);
    }
}
