package com.example.SchoolLunchReport.ai.aichatbot.dto;

import lombok.Data;

@Data
public class ChatbotRequestDto {
    private String category;
    private String question;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}