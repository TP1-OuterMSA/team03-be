package com.example.SchoolLunchReport.ai.analyze.service;

import com.example.SchoolLunchReport.ai.analyze.dto.FoodEvaluationRequestDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodEvaluationResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DjangoService {

    private final RestTemplate chatbotRestTemplate;

    @Value("${llm.food-analyze-url}")
    private String djangoBaseUrl;

    public DjangoService(@Qualifier("chatbotRestTemplate") RestTemplate chatbotRestTemplate) {
        this.chatbotRestTemplate = chatbotRestTemplate;
    }

    public FoodEvaluationResponseDto sendEvaluationsToDjango(FoodEvaluationRequestDto requestDto) {
        try {
            log.info("장고로 평가 데이터 전송 - 음식명: {}, 평가 개수: {}",
                    requestDto.getFoodName(), requestDto.getEvaluations().size());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<FoodEvaluationRequestDto> request = new HttpEntity<>(requestDto, headers);

            FoodEvaluationResponseDto response = chatbotRestTemplate.postForObject(
                    djangoBaseUrl, request, FoodEvaluationResponseDto.class);

            return response;
        } catch (Exception e) {
            log.error("장고 서비스와 통신 중 오류 발생: {}", e.getMessage());

            FoodEvaluationResponseDto errorResponse = new FoodEvaluationResponseDto();
            errorResponse.setFoodName(requestDto.getFoodName());
            errorResponse.setError("장고 서비스와 통신 중 오류가 발생했습니다: " + e.getMessage());
            return errorResponse;
        }
    }
}
