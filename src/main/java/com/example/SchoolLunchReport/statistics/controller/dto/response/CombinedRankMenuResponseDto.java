package com.example.SchoolLunchReport.statistics.controller.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CombinedRankMenuResponseDto(

    List<RankMenuResponseDto> topRankMenuResponseDto,
    List<RankMenuResponseDto> bottomRankMenuResponseDto

) {

    public static CombinedRankMenuResponseDto of(
        List<RankMenuResponseDto> topRankResponses,
        List<RankMenuResponseDto> bottomRankResponses
    ) {
        return CombinedRankMenuResponseDto.builder()
            .topRankMenuResponseDto(topRankResponses)
            .bottomRankMenuResponseDto(bottomRankResponses)
            .build();
    }
}
