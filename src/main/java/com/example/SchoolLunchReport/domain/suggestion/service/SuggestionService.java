package com.example.SchoolLunchReport.domain.suggestion.service;

import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.support.FoodReader;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.CreateSuggestionRequestDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.SuggestionSpecRequestDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.request.UpdateSuggestionRequestDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.response.DeleteSuggestionResponseDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.response.SuggestionListItemResponseDto;
import com.example.SchoolLunchReport.domain.suggestion.controller.dto.response.SuggestionResponseDto;
import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;
import com.example.SchoolLunchReport.domain.suggestion.repository.SuggestionJpaRepo;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional()
@RequiredArgsConstructor
public class SuggestionService {

    final SuggestionJpaRepo suggestionJpaRepo;
    final FoodReader foodReader;

    public String createSuggestion(CreateSuggestionRequestDto createSuggestionRequestDto) {
        Food food = foodReader.getFoodById(createSuggestionRequestDto.foodId());
        Suggestion suggestion = createSuggestionRequestDto.toEntity(food);
        suggestionJpaRepo.save(suggestion);
        return suggestion.getTitle();
    }

    @Transactional(readOnly = true)
    public List<SuggestionListItemResponseDto> getSuggestionList(Pageable pageable,
        SuggestionSpecRequestDto suggestionSpecRequestDto) {
        Page<Suggestion> suggestionPage = suggestionJpaRepo.findAll(pageable);

        return suggestionPage.stream()
            .map(SuggestionListItemResponseDto::from).toList();
    }

    @Transactional(readOnly = true)
    public SuggestionResponseDto getSuggestion(Long suggestionId) {
        Suggestion suggestion = findSuggestionById(suggestionId);
        return SuggestionResponseDto.toEntity(suggestion);
    }


    public SuggestionResponseDto updateSuggestion(
        Long suggestionId,
        UpdateSuggestionRequestDto updateSuggestionRequestDto
    ) {
        Food food = foodReader.getFoodById(updateSuggestionRequestDto.foodId());
        Suggestion suggestion = findSuggestionById(suggestionId);
        suggestion.update(updateSuggestionRequestDto, food);
        return SuggestionResponseDto.toEntity(suggestion);

    }

    public DeleteSuggestionResponseDto deleteSuggestion(Long suggestionId) {
        suggestionJpaRepo.deleteById(suggestionId);
        return DeleteSuggestionResponseDto
            .builder()
            .id(suggestionId)
            .build();
    }

    private Suggestion findSuggestionById(Long suggestionId) {
        return suggestionJpaRepo.findById(suggestionId).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 건의입니다.")
        );
    }
}
