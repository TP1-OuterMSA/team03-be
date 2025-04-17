package com.example.SchoolLunchReport.statistics.domain.desired.repo;

import com.example.SchoolLunchReport.statistics.domain.desired.entity.DesiredFood;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.FoodNameCountDto;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.QDesiredFood;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DesiredFoodJpaRepoImpl implements DesiredFoodRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FoodNameCountDto> findFoodNamesTopN(LocalDate startDate, LocalDate endDate, int n) {
        QDesiredFood df = QDesiredFood.desiredFood;

        return queryFactory
            .select(Projections.constructor(
                FoodNameCountDto.class,
                df.foodName,
                df.foodName.count()
            ))
            .from(df)
            .where(df.createdAt.between(startDate, endDate))
            .groupBy(df.foodName)
            .orderBy(df.foodName.count().desc())
            .limit(n)
            .fetch();
    }
}
