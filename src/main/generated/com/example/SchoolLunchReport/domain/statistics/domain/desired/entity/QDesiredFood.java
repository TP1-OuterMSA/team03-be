package com.example.SchoolLunchReport.domain.statistics.domain.desired.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDesiredFood is a Querydsl query type for DesiredFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDesiredFood extends EntityPathBase<DesiredFood> {

    private static final long serialVersionUID = 574255157L;

    public static final QDesiredFood desiredFood = new QDesiredFood("desiredFood");

    public final com.example.SchoolLunchReport.global.common.QBaseTimeEntity _super = new com.example.SchoolLunchReport.global.common.QBaseTimeEntity(this);

    //inherited
    public final DatePath<java.time.LocalDate> createdAt = _super.createdAt;

    public final StringPath foodName = createString("foodName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDesiredFood(String variable) {
        super(DesiredFood.class, forVariable(variable));
    }

    public QDesiredFood(Path<? extends DesiredFood> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDesiredFood(PathMetadata metadata) {
        super(DesiredFood.class, metadata);
    }

}

