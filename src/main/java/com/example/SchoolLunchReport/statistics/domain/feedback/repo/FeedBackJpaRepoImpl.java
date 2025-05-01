package com.example.SchoolLunchReport.statistics.domain.feedback.repo;

import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.QFoodMenu;
import com.example.SchoolLunchReport.product.food.domain.entity.QFood;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.CategoryScoreAvgDto;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.QFeedBack;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedBackJpaRepoImpl implements FeedbackRepositoryCustom {

    final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryScoreAvgDto> sumScoreByCategoryBetween(LocalDate startDate,
        LocalDate endDate) {
        QFeedBack fb = QFeedBack.feedBack;
        QFoodMenu fm = QFoodMenu.foodMenu;
        QFood food = QFood.food;

        return queryFactory
            .select(Projections.constructor(
                CategoryScoreAvgDto.class,
                food.category,
                fb.score.avg()
            ))
            .from(fb)
            .join(fb.foodMenu, fm)
            .join(fm.food, food)
            .where(fb.createdAt.between(startDate, endDate))
            .groupBy(food.category)
            .fetch();
    }
}
