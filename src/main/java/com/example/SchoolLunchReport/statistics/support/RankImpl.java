package com.example.SchoolLunchReport.statistics.support;

import com.example.SchoolLunchReport.statistics.controller.dto.response.response.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.domain.entity.FoodRank;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RankImpl {

    public List<RankMenuResponseDto> getRankMenuResponseDtoList(
        List<FoodRank> topFoodRanks) {
        return IntStream.range(0, topFoodRanks.size())
            .mapToObj(i -> RankMenuResponseDto.of(topFoodRanks.get(i), i + 1))
            .toList();
    }
}
