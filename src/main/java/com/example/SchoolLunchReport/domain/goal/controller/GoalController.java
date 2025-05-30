package com.example.SchoolLunchReport.domain.goal.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.domain.goal.controller.dto.request.CreateGoalRequestDto;
import com.example.SchoolLunchReport.domain.goal.controller.dto.request.UpdateGoalRequestDto;
import com.example.SchoolLunchReport.domain.goal.controller.dto.response.GoalResponseDto;
import com.example.SchoolLunchReport.domain.goal.service.GoalService;
import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ANALYTICS_TEAM_URL + "/statistics/goals")
public class GoalController implements GoalControllerDocs {

    final GoalService goalService;

    @Override
    @PostMapping
    public ApiResponse<?> createGoal(
        @RequestBody CreateGoalRequestDto createGoalRequestDto) {
        goalService.createGoal(createGoalRequestDto);
        return ApiResponse.success(SuccessType.CREATED);
    }

    @Override
    @GetMapping
    public ApiResponse<?> getGoalList() {
        List<GoalResponseDto> dto = goalService.getGoalList();
        return ApiResponse.success(SuccessType.SUCCESS, dto);
    }

    @Override
    @GetMapping("/{foodId}")
    public ApiResponse<?> getGoal(@PathVariable Long foodId) {
        GoalResponseDto goalResponseDto = goalService.getGoalByFood(foodId);
        return null;
    }

    @Override
    @PutMapping("/{goalId}")
    public ApiResponse<?> updateGoal(
        @PathVariable(name = "goalId") Long goalId,
        @RequestBody UpdateGoalRequestDto updateGoalRequestDto) {
        GoalResponseDto goalResponseDto = goalService.updateGoal(goalId, updateGoalRequestDto);

        return ApiResponse.success(SuccessType.SUCCESS, goalResponseDto);
    }

    @Override
    @DeleteMapping("/{goalId}")
    public ApiResponse<?> deleteGoal(
        @PathVariable(name = "goalId") Long goalId) {
        goalService.deleteGoal(goalId);
        return ApiResponse.success(SuccessType.SUCCESS);
    }

}
