package com.example.SchoolLunchReport.ai.aichatbot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "relevant_document")
@Getter
@Setter
@NoArgsConstructor
public class RelevantDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatbot_response_id")
    private ChatbotResponse chatbotResponse;

    @Column(columnDefinition = "TEXT")
    private String document;

    private Double similarity;
}