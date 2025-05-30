package com.example.SchoolLunchReport.domain.goal.service;

import com.example.SchoolLunchReport.domain.goal.controller.dto.request.CreateGoalRequestDto;
import com.example.SchoolLunchReport.domain.goal.controller.dto.request.UpdateGoalRequestDto;
import com.example.SchoolLunchReport.domain.goal.controller.dto.response.GoalResponseDto;
import com.example.SchoolLunchReport.domain.goal.domain.Goal;
import com.example.SchoolLunchReport.domain.goal.repo.GoalJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.support.FoodReader;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalService {

    final GoalJpaRepository goalJpaRepository;
    final FoodReader foodReader;

    public void createGoal(CreateGoalRequestDto createGoalRequestDto) {
        Food foodById = foodReader.getFoodById(createGoalRequestDto.foodId());
        Goal goal = createGoalRequestDto.toEntity(foodById);
        goalJpaRepository.save(goal);
    }

    @Transactional(readOnly = true)
    public List<GoalResponseDto> getGoalList() {
        List<Goal> all = goalJpaRepository.findAll();
        return all.stream()
            .map(GoalResponseDto::toEntity)
            .toList();
    }

    @Transactional(readOnly = true)
    public GoalResponseDto getGoalByFood(Long foodId) {
        Food food = foodReader.getFoodById(foodId);
        Goal goal = goalJpaRepository.findByFood(food);
        return GoalResponseDto.toEntity(goal);
    }

    public GoalResponseDto updateGoal(Long goalId, UpdateGoalRequestDto updateGoalRequestDto) {
        Goal goal = goalJpaRepository.findById(goalId).orElseThrow(
            () -> new EntityNotFoundException("없는 목표 아이디입니다.")
        );
        goal.update(updateGoalRequestDto);
        return GoalResponseDto.toEntity(goal);
    }

    public void deleteGoal(Long goalId) {
        goalJpaRepository.deleteById(goalId);
    }
}
