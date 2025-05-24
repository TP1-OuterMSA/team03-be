package com.example.SchoolLunchReport.ai.aichatbot.repository;

import com.example.SchoolLunchReport.ai.aichatbot.entity.ChatbotResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatbotResponseJpaRepository extends JpaRepository<ChatbotResponse, Long> {
    List<ChatbotResponse> findAllByOrderByCreatedAtDesc();
}
