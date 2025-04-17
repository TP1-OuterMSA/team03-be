package com.example.SchoolLunchReport.client.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AdjustMenuNameRequestDto(
    @JsonProperty("food_name") String foodName
) {

    public static AdjustMenuNameRequestDto from(String food_name) {
        return new AdjustMenuNameRequestDto(food_name);
    }
}
