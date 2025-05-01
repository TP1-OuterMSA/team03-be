package com.example.SchoolLunchReport.statistics.domain.desired.service;

import com.example.SchoolLunchReport.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.FoodNameCountDto;
import com.example.SchoolLunchReport.statistics.domain.desired.impl.DesiredFoodMapper;
import com.example.SchoolLunchReport.statistics.domain.desired.impl.DesiredFoodReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DesiredFoodService {

    final DesiredFoodReader desiredFoodReader;
    final DesiredFoodMapper desiredFoodMapper;

    public List<DesiredFoodResponseDto> getDesiredFoodTopN(Boundary boundary, int i) {
        List<FoodNameCountDto> foodNameCountDtos = desiredFoodReader.getDesiredFoodInBoundaryTopN(
            boundary.startDate(),
            boundary.endDate(), i);
        return desiredFoodMapper.mapResponseDto(foodNameCountDtos);
    }
}
