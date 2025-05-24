package com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.QFoodMenu;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.QFood;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.QMenu;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.CategoryScoreAvgDto;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.QFeedBack;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
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

    @Override
    public List<FeedBack> getFeedBackByMenu(List<Menu> menuList) {
        QFeedBack fb = QFeedBack.feedBack;
        QFoodMenu mf = QFoodMenu.foodMenu;
        QMenu menu = QMenu.menu;
        return queryFactory
            .select(fb)
            .from(fb)
            .join(fb.foodMenu, mf).fetchJoin()
            .join(mf.menu, menu).fetchJoin()
            .where(menu.in(menuList))
            .fetch();
    }
}
