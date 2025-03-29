package com.example.SchoolLunchReport.statistics.rank.controller;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import com.example.SchoolLunchReport.statistics.rank.controller.dto.RankMenuResponseDto;
import com.example.SchoolLunchReport.statistics.rank.domain.type.Period;
import com.example.SchoolLunchReport.statistics.rank.service.RankService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class RankController implements RankControllerDocs {

    final RankService rankService;

    @Override
    @GetMapping("/rank")
    public ApiResponse<?>

    getRankMenu(
        @RequestParam Period period
    ) {
        RankMenuResponseDto rankMenuResponseDto = rankService.getRankMenu(period);
        return ApiResponse.success(SuccessType.SUCCESS, rankMenuResponseDto);
    }
}
