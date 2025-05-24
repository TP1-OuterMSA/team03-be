package com.example.SchoolLunchReport.domain.suggestion.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSuggestion is a Querydsl query type for Suggestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSuggestion extends EntityPathBase<Suggestion> {

    private static final long serialVersionUID = -1429890854L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSuggestion suggestion = new QSuggestion("suggestion");

    public final com.example.SchoolLunchReport.global.common.QBaseTimeEntity _super = new com.example.SchoolLunchReport.global.common.QBaseTimeEntity(this);

    public final EnumPath<com.example.SchoolLunchReport.domain.product.food.domain.type.Category> category = createEnum("category", com.example.SchoolLunchReport.domain.product.food.domain.type.Category.class);

    public final StringPath content = createString("content");

    //inherited
    public final DatePath<java.time.LocalDate> createdAt = _super.createdAt;

    public final com.example.SchoolLunchReport.domain.product.food.domain.entity.QFood food;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath title = createString("title");

    public QSuggestion(String variable) {
        this(Suggestion.class, forVariable(variable), INITS);
    }

    public QSuggestion(Path<? extends Suggestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSuggestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSuggestion(PathMetadata metadata, PathInits inits) {
        this(Suggestion.class, metadata, inits);
    }

    public QSuggestion(Class<? extends Suggestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.food = inits.isInitialized("food") ? new com.example.SchoolLunchReport.domain.product.food.domain.entity.QFood(forProperty("food")) : null;
    }

}

