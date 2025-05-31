package com.example.SchoolLunchReport.domain.goal.controller;

import com.example.SchoolLunchReport.domain.goal.controller.dto.request.CreateGoalRequestDto;
import com.example.SchoolLunchReport.domain.goal.controller.dto.request.UpdateGoalRequestDto;
import com.example.SchoolLunchReport.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "목표 관리 관련 API")
public interface GoalControllerDocs {

    @Operation(summary = "목표 등록")
    ApiResponse<?> createGoal(
        CreateGoalRequestDto createGoalRequestDto
    );

    @Operation(summary = "목표 목록 조회")
    ApiResponse<?> getGoalList();

    @Operation(summary = "특정 음식 목표 조회")
    ApiResponse<?> getGoal(Long foodId);

    @Operation(summary = "특정 음식 목표 수정(PUT)")
    ApiResponse<?> updateGoal(Long goalId, UpdateGoalRequestDto updateGoalRequestDto);

    @Operation(summary = "특정 음식 목표 삭제")
    ApiResponse<?> deleteGoal(Long goalId);
}
