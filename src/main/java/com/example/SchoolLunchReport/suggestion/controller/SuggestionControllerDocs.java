package com.example.SchoolLunchReport.suggestion.controller;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.suggestion.controller.dto.request.CreateSuggestionRequestDto;
import com.example.SchoolLunchReport.suggestion.controller.dto.request.SuggestionSpecRequestDto;
import com.example.SchoolLunchReport.suggestion.controller.dto.request.UpdateSuggestionRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "건의함 관련된 api")
public interface SuggestionControllerDocs {

    @Operation(summary = "건의 등록 api")
    ApiResponse<?> createSuggestion(CreateSuggestionRequestDto createSuggestionRequestDto);

    @Operation(summary = "건의 목록 조회 api")
    ApiResponse<?> getSuggestionList(Pageable pageable,
        SuggestionSpecRequestDto suggestionSpecRequestDto);

    @Operation(summary = "건의 상세 조회 api")
    ApiResponse<?> getSuggestion(Long suggestionId);

    @Operation(summary = "건의 수정 put api")
    ApiResponse<?> updateSuggestion(Long suggestionId,
        UpdateSuggestionRequestDto updateSuggestionRequestDto);

    @Operation(summary = "건의 삭제 api")
    ApiResponse<?> deleteSuggestion(Long suggestionId);

}
