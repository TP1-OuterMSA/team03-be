package com.example.SchoolLunchReport.aichatbot.service;
import com.example.SchoolLunchReport.aichatbot.dto.ChatbotRequestDto;
import com.example.SchoolLunchReport.aichatbot.dto.ChatbotResponseDto;
import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.product.evaluation.repository.EvaluationJpaRepository;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.feedback.repo.FeedBackJpaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class AiChatbotService {
    private final MenuJpaRepository menuJpaRepository;
    private final EvaluationJpaRepository evaluationJpaRepository;
    private final FoodMenuJpaRepository foodMenuJpaRepository;
    private final FeedBackJpaRepo feedBackJpaRepository;
    private final FoodJpaRepository foodJpaRepository;

    @Qualifier("chatbotRestTemplate")
    private final RestTemplate chatbotRestTemplate;
    // private static final String DJANGO_BASE_URL = "http://127.0.0.1:8000/api/team3/llmchatbot/basic_chatbot_request/";
    private static final String DJANGO_BASE_URL = "http://k8s-msaservices-7d023f0bb9-676035063.ap-northeast-2.elb.amazonaws.com/api/team3/llmchatbot/basic_chatbot_request/";

    public ChatbotResponseDto forwardToDjango(ChatbotRequestDto requestDto) {
        try {
            log.info("Forwarding request to Django - Category: {}, Question: {}",
                    requestDto.getCategory(), requestDto.getQuestion());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ChatbotRequestDto> request = new HttpEntity<>(requestDto, headers);

            ChatbotResponseDto response = chatbotRestTemplate.postForObject(
                    DJANGO_BASE_URL, request, ChatbotResponseDto.class);

            return response;
        } catch (Exception e) {
            log.error("Error forwarding request to Django: {}", e.getMessage());

            ChatbotResponseDto errorResponse = new ChatbotResponseDto();
            errorResponse.setMessage("Django 서비스와 통신 중 오류가 발생했습니다");
            errorResponse.setAnswer(null);

            return errorResponse;
        }
    }

    public List<String> getProcessedFoodData() {
        List<Food> allFoods = foodJpaRepository.findAll();
        log.info("Retrieved {} food items", allFoods.size());

        return allFoods.stream()
                .map(this::processFood)
                .collect(Collectors.toList());
    }

    private String processFood(Food food) {
        StringBuilder sb = new StringBuilder();
        sb.append(food.getName())
                .append("의 영양소는 ")
                .append(food.getNutrition())
                .append("이며, 칼로리는 ")
                .append(food.getCalorie())
                .append("kcal이고, 알러지 정보는 ")
                .append(food.getAllergy())
                .append("입니다.");

        return sb.toString();
    }

    public List<String> getProcessedFeedbackData() {
        List<FeedBack> allFeedbacks = feedBackJpaRepository.findAll();
        log.info("Retrieved {} feedback items", allFeedbacks.size());
        List<String> processedData = new ArrayList<>();
        for (FeedBack feedback : allFeedbacks) {
            try {
                FoodMenu foodMenu = feedback.getFoodMenu();
                Food food = foodMenu.getFood();

                String processedFeedback = String.format(
                        "%s는 5점 만점 중 %.1f점을 받았으며, \"%s\"라는 평가를 받았습니다.",
                        food.getName(),
                        feedback.getScore(),
                        feedback.getEvaluation()
                );
                processedData.add(processedFeedback);
            } catch (Exception e) {
                log.error("Error processing feedback item: {}", e.getMessage());
            }
        }
        return processedData;
    }
}