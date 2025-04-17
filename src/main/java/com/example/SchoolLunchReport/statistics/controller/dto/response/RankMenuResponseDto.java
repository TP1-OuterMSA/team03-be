package com.example.SchoolLunchReport.statistics.controller.dto.response;

import com.example.SchoolLunchReport.statistics.domain.rank.entity.FoodRank;
import lombok.Builder;

@Builder
public record RankMenuResponseDto(
    Long id,
    String name,
    Double score,
    Integer rankChange,
    Integer rank
) {

    public static RankMenuResponseDto of(FoodRank foodRank, int rank) {
        return RankMenuResponseDto.builder()
            .id(foodRank.getFood().getId())
            .name(foodRank.getFood().getName())
            .score(foodRank.getScore())
            .rank(rank)
            .rankChange(foodRank.getRankGap())
            .build();
    }
}
