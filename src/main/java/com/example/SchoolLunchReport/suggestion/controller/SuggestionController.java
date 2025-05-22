package com.example.SchoolLunchReport.suggestion.controller;

import static com.example.SchoolLunchReport.global.common.Constants.ANALYTICS_TEAM_URL;

import com.example.SchoolLunchReport.global.response.ApiResponse;
import com.example.SchoolLunchReport.global.response.type.SuccessType;
import com.example.SchoolLunchReport.suggestion.controller.dto.request.CreateSuggestionRequestDto;
import com.example.SchoolLunchReport.suggestion.controller.dto.request.SuggestionSpecRequestDto;
import com.example.SchoolLunchReport.suggestion.controller.dto.request.UpdateSuggestionRequestDto;
import com.example.SchoolLunchReport.suggestion.controller.dto.response.DeleteSuggestionResponseDto;
import com.example.SchoolLunchReport.suggestion.controller.dto.response.SuggestionListItemResponseDto;
import com.example.SchoolLunchReport.suggestion.controller.dto.response.SuggestionResponseDto;
import com.example.SchoolLunchReport.suggestion.service.SuggestionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ANALYTICS_TEAM_URL + "/suggestions")
@RequiredArgsConstructor
public class SuggestionController implements SuggestionControllerDocs {

    final SuggestionService suggestionService;

    @Override
    @PostMapping
    public ApiResponse<?> createSuggestion(CreateSuggestionRequestDto createSuggestionRequestDto) {
        String suggestionTitle = suggestionService.createSuggestion(createSuggestionRequestDto);
        return ApiResponse.success(SuccessType.CREATED, suggestionTitle);
    }

    @Override
    @GetMapping
    public ApiResponse<?> getSuggestionList(
        @PageableDefault(size = 10) Pageable pageable,
        @Valid @ModelAttribute SuggestionSpecRequestDto suggestionSpecRequestDto
    ) {
        List<SuggestionListItemResponseDto> suggestionListItemResponseDtos =
            suggestionService.getSuggestionList(pageable, suggestionSpecRequestDto);
        return ApiResponse.success(SuccessType.SUCCESS, suggestionListItemResponseDtos);
    }

    @Override
    @GetMapping("/{suggestionId}")
    public ApiResponse<?> getSuggestion(
        @PathVariable(name = "suggestionId") Long suggestionId
    ) {
        SuggestionResponseDto suggestionResponseDto = suggestionService.getSuggestion(suggestionId);
        return ApiResponse.success(SuccessType.SUCCESS, suggestionResponseDto);
    }

    @Override
    @PutMapping("/{suggestionId}")
    public ApiResponse<?> updateSuggestion(
        @PathVariable(name = "suggestionId") Long suggestionId,
        @RequestBody UpdateSuggestionRequestDto updateSuggestionRequestDto
    ) {
        SuggestionResponseDto suggestionResponseDto = suggestionService.updateSuggestion(
            suggestionId,
            updateSuggestionRequestDto
        );
        return ApiResponse.success(SuccessType.SUCCESS, suggestionResponseDto);
    }

    @Override
    @DeleteMapping("/{suggestionId}")
    public ApiResponse<?> deleteSuggestion(
        @PathVariable(name = "suggestionId") Long suggestionId
    ) {
        DeleteSuggestionResponseDto deleteSuggestion =
            suggestionService.deleteSuggestion(suggestionId);
        return ApiResponse.success(SuccessType.SUCCESS, deleteSuggestion);
    }
}
