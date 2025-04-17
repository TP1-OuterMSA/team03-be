package com.example.SchoolLunchReport.statistics.domain.desired.service;

import com.example.SchoolLunchReport.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.DesiredFood;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.FoodNameCountDto;
import com.example.SchoolLunchReport.statistics.domain.desired.impl.DesiredFoodMapper;
import com.example.SchoolLunchReport.statistics.domain.desired.impl.DesiredFoodReader;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DesiredFoodService {

    final DesiredFoodReader desiredFoodReader;
    final DesiredFoodMapper desiredFoodMapper;

    public List<DesiredFoodResponseDto> getDesiredFoodTopN(LocalDate startDate, LocalDate endDate,
        int i) {
        List<FoodNameCountDto> foodNameCountDtos = desiredFoodReader.getDesiredFoodInBoundaryTopN(
            startDate,
            endDate, i);
        return desiredFoodMapper.mapResponseDto(foodNameCountDtos);
    }
}
