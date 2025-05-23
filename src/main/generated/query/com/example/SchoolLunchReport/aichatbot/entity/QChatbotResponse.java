package com.example.SchoolLunchReport.aichatbot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatbotResponse is a Querydsl query type for ChatbotResponse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatbotResponse extends EntityPathBase<ChatbotResponse> {

    private static final long serialVersionUID = -1476818847L;

    public static final QChatbotResponse chatbotResponse = new QChatbotResponse("chatbotResponse");

    public final StringPath answer = createString("answer");

    public final StringPath chainOfThought = createString("chainOfThought");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final StringPath question = createString("question");

    public final ListPath<RelevantDocument, QRelevantDocument> relevantDocuments = this.<RelevantDocument, QRelevantDocument>createList("relevantDocuments", RelevantDocument.class, QRelevantDocument.class, PathInits.DIRECT2);

    public QChatbotResponse(String variable) {
        super(ChatbotResponse.class, forVariable(variable));
    }

    public QChatbotResponse(Path<? extends ChatbotResponse> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatbotResponse(PathMetadata metadata) {
        super(ChatbotResponse.class, metadata);
    }

}

