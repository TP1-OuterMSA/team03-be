package com.example.SchoolLunchReport.ai.aichatbot.service;
import com.example.SchoolLunchReport.ai.aichatbot.dto.AgentChatbotRequestDto;
import com.example.SchoolLunchReport.ai.aichatbot.dto.AgentChatbotResponseDto;
import com.example.SchoolLunchReport.ai.aichatbot.dto.ChatbotRequestDto;
import com.example.SchoolLunchReport.ai.aichatbot.dto.ChatbotResponseDto;
import com.example.SchoolLunchReport.ai.aichatbot.repository.ChatbotResponseJpaRepository;
import com.example.SchoolLunchReport.ai.aichatbot.repository.RelevantDocumentJpaRepository;
import com.example.SchoolLunchReport.ai.aichatbot.entity.ChatbotResponse;
import com.example.SchoolLunchReport.ai.aichatbot.entity.RelevantDocument;
import com.example.SchoolLunchReport.ai.aichatbot.util.StringSimilarityUtil;
import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.domain.product.evaluation.repository.EvaluationJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.domain.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.FeedBackJpaRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final ChatbotResponseJpaRepository chatbotResponseJpaRepository;
    private final RelevantDocumentJpaRepository relevantDocumentJpaRepository;


    @Qualifier("chatbotRestTemplate")
    private final RestTemplate chatbotRestTemplate;

    @Value("${llm.base-url}")
    private String djangoBaseUrl;

    @Value("${llm.agent-url}")
    private String djangoAgentUrl;

    public ChatbotResponseDto forwardToDjango(ChatbotRequestDto requestDto) {
        try {
            log.info("Forwarding request to Django - Category: {}, Question: {}",
                    requestDto.getCategory(), requestDto.getQuestion());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ChatbotRequestDto> request = new HttpEntity<>(requestDto, headers);

            ChatbotResponseDto response = chatbotRestTemplate.postForObject(
                    djangoBaseUrl, request, ChatbotResponseDto.class);

            return response;
        } catch (Exception e) {
            log.error("Error forwarding request to Django: {}", e.getMessage());

            ChatbotResponseDto errorResponse = new ChatbotResponseDto();
            errorResponse.setMessage("Django 서비스와 통신 중 오류가 발생했습니다");
            errorResponse.setAnswer(null);

            return errorResponse;
        }
    }

    @Transactional
    public AgentChatbotResponseDto forwardAgentToDjango(AgentChatbotRequestDto requestDto) {
        try {
            String question = requestDto.getQuestion();
            log.info("Checking for similar questions in database for question: {}", question);

            ChatbotResponse cachedResponse = findSimilarQuestion(question);
            if (cachedResponse != null) {
                log.info("Found similar question in cache. Returning cached response.");
                return mapToResponseDto(cachedResponse);
            }

            log.info("No similar question found. Forwarding agent request to Django - Question: {}", question);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<AgentChatbotRequestDto> request = new HttpEntity<>(requestDto, headers);

            AgentChatbotResponseDto response = chatbotRestTemplate.postForObject(
                    djangoAgentUrl, request, AgentChatbotResponseDto.class);

            if (response != null && response.getAnswer() != null) {
                saveChatbotResponse(question, response);
            }

            return response;
        } catch (Exception e) {
            log.error("Error forwarding agent request to Django: {}", e.getMessage());

            AgentChatbotResponseDto errorResponse = new AgentChatbotResponseDto();
            errorResponse.setMessage("Django 서비스와 통신 중 오류가 발생했습니다");
            errorResponse.setAnswer(null);
            errorResponse.setChainOfThought(null);

            return errorResponse;
        }
    }

    private ChatbotResponse findSimilarQuestion(String question) {
        List<ChatbotResponse> allResponses = chatbotResponseJpaRepository.findAllByOrderByCreatedAtDesc();

        for (ChatbotResponse response : allResponses) {
            double similarity = StringSimilarityUtil.calculateSimilarity(question, response.getQuestion());
            log.debug("Similarity with question '{}': {}", response.getQuestion(), similarity);

            if (similarity >= 0.8) {
                log.info("Cache hit! Similarity: {} with question: {}", similarity, response.getQuestion());
                return response;
            }
        }

        return null;
    }

    @Transactional
    protected void saveChatbotResponse(String question, AgentChatbotResponseDto responseDto) {
        try {
            ChatbotResponse chatbotResponse = new ChatbotResponse();
            chatbotResponse.setQuestion(question);
            chatbotResponse.setMessage(responseDto.getMessage());
            chatbotResponse.setAnswer(responseDto.getAnswer());
            chatbotResponse.setChainOfThought(responseDto.getChainOfThought());

            if (responseDto.getRelevant_documents() != null) {
                for (Map<String, Object> docInfo : responseDto.getRelevant_documents()) {
                    RelevantDocument relevantDocument = new RelevantDocument();
                    relevantDocument.setDocument((String) docInfo.get("document"));
                    relevantDocument.setSimilarity(((Number) docInfo.get("similarity")).doubleValue());
                    chatbotResponse.addRelevantDocument(relevantDocument);
                }
            }

            chatbotResponseJpaRepository.save(chatbotResponse);
            log.info("Saved chatbot response to database with ID: {}", chatbotResponse.getId());
        } catch (Exception e) {
            log.error("Failed to save chatbot response: {}", e.getMessage(), e);
        }
    }

    private AgentChatbotResponseDto mapToResponseDto(ChatbotResponse chatbotResponse) {
        AgentChatbotResponseDto dto = new AgentChatbotResponseDto();
        dto.setMessage(chatbotResponse.getMessage());
        dto.setAnswer(chatbotResponse.getAnswer());
        dto.setChainOfThought(chatbotResponse.getChainOfThought());

        List<Map<String, Object>> relevantDocs = new ArrayList<>();
        for (RelevantDocument doc : chatbotResponse.getRelevantDocuments()) {
            Map<String, Object> docMap = new HashMap<>();
            docMap.put("document", doc.getDocument());
            docMap.put("similarity", doc.getSimilarity());
            relevantDocs.add(docMap);
        }
        dto.setRelevant_documents(relevantDocs);

        return dto;
    }




    // 데이터 요청 부분

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