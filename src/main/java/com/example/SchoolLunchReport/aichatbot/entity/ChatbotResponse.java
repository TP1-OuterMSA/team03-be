package com.example.SchoolLunchReport.aichatbot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatbot_response")
@Getter
@Setter
@NoArgsConstructor
public class ChatbotResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(columnDefinition = "TEXT")
    private String chainOfThought;

    @OneToMany(mappedBy = "chatbotResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelevantDocument> relevantDocuments = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void addRelevantDocument(RelevantDocument document) {
        this.relevantDocuments.add(document);
        document.setChatbotResponse(this);
    }
}