package com.example.SchoolLunchReport.domain.product.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record CreateMenuRequestDto(
    @JsonProperty("RICE")
    String rice,

    @JsonProperty("SOUP")
    String soup,

    @JsonProperty("MAIN_DISH")
    String mainDish,

    @JsonProperty("SIDE_DISH")
    String sideDish,

    @JsonProperty("DESSERT")
    String dessert,

    List<String> hashtags
) {

}

