package com.example.SchoolLunchReport.domain.product.controller.dto.response;

import java.util.List;

public record CreateMenuResponseDto(
    Long menuId,
    List<String> allergies
) {

}
