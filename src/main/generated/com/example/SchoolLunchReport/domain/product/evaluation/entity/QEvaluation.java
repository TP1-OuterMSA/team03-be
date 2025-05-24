package com.example.SchoolLunchReport.domain.product.evaluation.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvaluation is a Querydsl query type for Evaluation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvaluation extends EntityPathBase<Evaluation> {

    private static final long serialVersionUID = -1586406327L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvaluation evaluation1 = new QEvaluation("evaluation1");

    public final StringPath evaluation = createString("evaluation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.example.SchoolLunchReport.domain.product.menu.domain.entity.QMenu menu;

    public QEvaluation(String variable) {
        this(Evaluation.class, forVariable(variable), INITS);
    }

    public QEvaluation(Path<? extends Evaluation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvaluation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvaluation(PathMetadata metadata, PathInits inits) {
        this(Evaluation.class, metadata, inits);
    }

    public QEvaluation(Class<? extends Evaluation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.menu = inits.isInitialized("menu") ? new com.example.SchoolLunchReport.domain.product.menu.domain.entity.QMenu(forProperty("menu")) : null;
    }

}

