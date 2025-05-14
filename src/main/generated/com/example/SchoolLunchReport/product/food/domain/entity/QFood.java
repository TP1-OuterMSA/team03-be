package com.example.SchoolLunchReport.product.food.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<Food> {

    private static final long serialVersionUID = 1748362811L;

    public static final QFood food = new QFood("food");

    public final StringPath allergy = createString("allergy");

    public final NumberPath<Double> calorie = createNumber("calorie", Double.class);

    public final EnumPath<com.example.SchoolLunchReport.product.food.domain.type.Category> category = createEnum("category", com.example.SchoolLunchReport.product.food.domain.type.Category.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nutrition = createString("nutrition");

    public final EnumPath<com.example.SchoolLunchReport.product.food.domain.type.SubCategory> subCategory = createEnum("subCategory", com.example.SchoolLunchReport.product.food.domain.type.SubCategory.class);

    public QFood(String variable) {
        super(Food.class, forVariable(variable));
    }

    public QFood(Path<? extends Food> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFood(PathMetadata metadata) {
        super(Food.class, metadata);
    }

}

