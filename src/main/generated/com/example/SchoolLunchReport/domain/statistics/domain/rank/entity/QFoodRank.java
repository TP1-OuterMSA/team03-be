package com.example.SchoolLunchReport.domain.statistics.domain.rank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFoodRank is a Querydsl query type for FoodRank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFoodRank extends EntityPathBase<FoodRank> {

    private static final long serialVersionUID = 779511975L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFoodRank foodRank = new QFoodRank("foodRank");

    public final com.example.SchoolLunchReport.domain.product.food.domain.entity.QFood food;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType> periodType = createEnum("periodType", com.example.SchoolLunchReport.domain.statistics.domain.boundary.type.PeriodType.class);

    public final NumberPath<Integer> previousRanking = createNumber("previousRanking", Integer.class);

    public final NumberPath<Integer> ranking = createNumber("ranking", Integer.class);

    public final DatePath<java.time.LocalDate> registerDate = createDate("registerDate", java.time.LocalDate.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public QFoodRank(String variable) {
        this(FoodRank.class, forVariable(variable), INITS);
    }

    public QFoodRank(Path<? extends FoodRank> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFoodRank(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFoodRank(PathMetadata metadata, PathInits inits) {
        this(FoodRank.class, metadata, inits);
    }

    public QFoodRank(Class<? extends FoodRank> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.food = inits.isInitialized("food") ? new com.example.SchoolLunchReport.domain.product.food.domain.entity.QFood(forProperty("food")) : null;
    }

}

