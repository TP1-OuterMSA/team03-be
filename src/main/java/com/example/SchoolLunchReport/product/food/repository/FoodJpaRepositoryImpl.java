package com.example.SchoolLunchReport.product.food.repository;

import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.QFoodMenu;
import com.example.SchoolLunchReport.product.food.domain.entity.QFood;
import com.example.SchoolLunchReport.product.food.repository.dto.FoodFrequencyDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FoodJpaRepositoryImpl implements FoodRepositoryCustom {

    final JPAQueryFactory queryFactory;

    @Override
    public List<FoodFrequencyDto> countByCategoryBetween(LocalDate startDate,
        LocalDate endDate) {

        QFoodMenu fm = QFoodMenu.foodMenu;
        QFood food = QFood.food;
        return queryFactory
            .select(Projections.constructor(
                FoodFrequencyDto.class,
                food.category,
                food.subCategory,
                food.subCategory.count()
            ))
            .from(fm)
            .join(fm.food, food)
            .where(fm.menu.date.between(startDate, endDate))
            .groupBy(food.subCategory)
            .fetch();
    }
}
