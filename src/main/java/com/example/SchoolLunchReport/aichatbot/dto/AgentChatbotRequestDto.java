package com.example.SchoolLunchReport.aichatbot.dto;

import lombok.Data;

@Data
public class AgentChatbotRequestDto {
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}