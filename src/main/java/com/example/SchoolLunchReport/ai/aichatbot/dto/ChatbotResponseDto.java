package com.example.SchoolLunchReport.ai.aichatbot.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class ChatbotResponseDto {
    private String message;
    private String answer;
    private List<Map<String, Object>> relevant_documents;
}