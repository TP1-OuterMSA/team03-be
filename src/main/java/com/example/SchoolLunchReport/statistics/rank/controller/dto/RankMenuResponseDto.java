package com.example.SchoolLunchReport.statistics.rank.controller.dto;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import java.util.List;
import lombok.Builder;

@Builder
public record RankMenuResponseDto(

    List<RankMenuResponse> topRankMenuResponse,
    List<RankMenuResponse> bottomRankMenuResponse

) {

    public static RankMenuResponseDto of(
        List<RankMenuResponse> topRankResponses,
        List<RankMenuResponse> bottomRankResponses
    ) {
        return RankMenuResponseDto.builder()
            .topRankMenuResponse(topRankResponses)
            .bottomRankMenuResponse(bottomRankResponses)
            .build();
    }
}
