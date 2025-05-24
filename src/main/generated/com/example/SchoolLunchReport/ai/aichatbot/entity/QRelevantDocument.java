package com.example.SchoolLunchReport.ai.aichatbot.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRelevantDocument is a Querydsl query type for RelevantDocument
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRelevantDocument extends EntityPathBase<RelevantDocument> {

    private static final long serialVersionUID = -1897635733L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRelevantDocument relevantDocument = new QRelevantDocument("relevantDocument");

    public final QChatbotResponse chatbotResponse;

    public final StringPath document = createString("document");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> similarity = createNumber("similarity", Double.class);

    public QRelevantDocument(String variable) {
        this(RelevantDocument.class, forVariable(variable), INITS);
    }

    public QRelevantDocument(Path<? extends RelevantDocument> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRelevantDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRelevantDocument(PathMetadata metadata, PathInits inits) {
        this(RelevantDocument.class, metadata, inits);
    }

    public QRelevantDocument(Class<? extends RelevantDocument> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatbotResponse = inits.isInitialized("chatbotResponse") ? new QChatbotResponse(forProperty("chatbotResponse")) : null;
    }

}

