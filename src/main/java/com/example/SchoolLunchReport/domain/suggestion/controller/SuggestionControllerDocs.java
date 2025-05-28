package com.example.SchoolLunchReport.domain.suggestion.controller;

import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.CreateAnswerRequestDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.CreateSuggestionRequestDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.SuggestionSpecRequestDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.UpdateAnswerRequestDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.UpdateSuggestionRequestDto;
import com.example.SchoolLunchReport.global.response.ApiResponse;
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

    //

    @Operation(summary = "답변 등록 api")
    ApiResponse<?> createAnswer(Long suggestionId, CreateAnswerRequestDto createAnswerRequestDto);

    @Operation(summary = "답변 수정 put api")
    ApiResponse<?> updateAnswer(
        Long suggestionId,
        Long answersId,
        UpdateAnswerRequestDto updateAnswerRequestDto
    );

    @Operation(summary = "답변 삭제 api")
    ApiResponse<?> deleteAnswer(
        Long suggestionId, Long answersId
    );
}
