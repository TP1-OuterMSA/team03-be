package com.example.SchoolLunchReport.client;

import com.example.SchoolLunchReport.client.dto.request.AdjustMenuNameRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class LLMClient {

    private final RestTemplate restTemplate;

    @Value("${llm.adjust-api-url}")
    private String ADJUST_API_URL;

    public String adjustMenuApi(
        AdjustMenuNameRequestDto adjustMenuNameRequestDto
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(adjustMenuNameRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 직렬화 실패", e);
        }
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        JsonNode jsonNode = restTemplate.postForObject(
            ADJUST_API_URL,
            request,
            JsonNode.class
        );
        assert jsonNode != null;
        return removeSurroundingQuotes(jsonNode.get("food_name").toString());
    }

    private String removeSurroundingQuotes(String input) {
        if (input == null) {
            return null;
        }
        if (input.startsWith("\"") && input.endsWith("\"") && input.length() >= 2) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }
}
