package com.example.SchoolLunchReport.domain.statistics.domain.desired.service;

import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.domain.statistics.domain.desired.impl.DesiredFoodMapper;
import com.example.SchoolLunchReport.domain.statistics.domain.desired.impl.DesiredFoodReader;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import com.example.SchoolLunchReport.domain.statistics.domain.desired.entity.FoodNameCountDto;
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
