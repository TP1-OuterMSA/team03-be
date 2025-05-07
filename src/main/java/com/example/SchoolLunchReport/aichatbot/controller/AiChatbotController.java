package com.example.SchoolLunchReport.aichatbot.controller;
import com.example.SchoolLunchReport.aichatbot.dto.ChatbotRequestDto;
import com.example.SchoolLunchReport.aichatbot.dto.ChatbotResponseDto;
import com.example.SchoolLunchReport.aichatbot.service.AiChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team3/analytics/chatbot")
public class AiChatbotController {
    private final AiChatbotService aiChatbotService;
    @PostMapping("/basic-chatbot-request")
    public ResponseEntity<?> basicChatbotRequest(@RequestBody ChatbotRequestDto requestDto) {
        ChatbotResponseDto responseDto = aiChatbotService.forwardToDjango(requestDto);
        return ResponseEntity.ok(responseDto);
    }
    @GetMapping("/food-info")
    public ResponseEntity<List<String>> getFoodInfo() {
        List<String> processedFoodData = aiChatbotService.getProcessedFoodData();
        return ResponseEntity.ok(processedFoodData);
    }
    @GetMapping("/feedback-info")
    public ResponseEntity<List<String>> getFeedbackInfo() {
        List<String> processedFeedbackData = aiChatbotService.getProcessedFeedbackData();
        return ResponseEntity.ok(processedFeedbackData);
    }
}