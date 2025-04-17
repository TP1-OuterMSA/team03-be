package com.example.SchoolLunchReport.statistics.domain.feedback.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedBack is a Querydsl query type for FeedBack
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedBack extends EntityPathBase<FeedBack> {

    private static final long serialVersionUID = 703385627L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedBack feedBack = new QFeedBack("feedBack");

    public final com.example.SchoolLunchReport.global.common.QBaseTimeEntity _super = new com.example.SchoolLunchReport.global.common.QBaseTimeEntity(this);

    //inherited
    public final DatePath<java.time.LocalDate> createdAt = _super.createdAt;

    public final com.example.SchoolLunchReport.product.FoodMenu.domain.entity.QFoodMenu foodMenu;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public QFeedBack(String variable) {
        this(FeedBack.class, forVariable(variable), INITS);
    }

    public QFeedBack(Path<? extends FeedBack> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedBack(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedBack(PathMetadata metadata, PathInits inits) {
        this(FeedBack.class, metadata, inits);
    }

    public QFeedBack(Class<? extends FeedBack> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.foodMenu = inits.isInitialized("foodMenu") ? new com.example.SchoolLunchReport.product.FoodMenu.domain.entity.QFoodMenu(forProperty("foodMenu"), inits.get("foodMenu")) : null;
    }

}

