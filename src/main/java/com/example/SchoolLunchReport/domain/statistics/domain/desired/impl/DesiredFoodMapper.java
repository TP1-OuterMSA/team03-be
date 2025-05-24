package com.example.SchoolLunchReport.domain.statistics.domain.desired.impl;

import com.example.SchoolLunchReport.domain.statistics.controller.dto.response.DesiredFoodResponseDto;
import com.example.SchoolLunchReport.domain.statistics.domain.desired.entity.FoodNameCountDto;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class DesiredFoodMapper {

    public List<DesiredFoodResponseDto> mapResponseDto(List<FoodNameCountDto> desiredFoodList) {
        return IntStream.range(0, desiredFoodList.size())
            .mapToObj(i -> DesiredFoodResponseDto.of(desiredFoodList.get(i), i + 1))
            .toList();
    }
}
