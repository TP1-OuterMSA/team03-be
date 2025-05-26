package com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.dto.CategoryScoreAvgDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackJpaRepo extends JpaRepository<FeedBack, Long> {

    List<FeedBack> findByCreatedAtBetween(LocalDate createdAt, LocalDate createdAt2);

    boolean existsByFoodMenuId(Long foodMenuId);

    List<FeedBack> findByCreatedAt(LocalDate startDate);

    List<FeedBack> findByFoodMenu(FoodMenu foodMenu);

    List<FeedBack> findByFoodMenuIdInAndCreatedAtBetween(
        List<Long> foodMenuIds,
        LocalDate startDate,
        LocalDate endDate);

    @Query("""
            SELECT new com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.dto.CategoryScoreAvgDto(f.category, AVG(fb.score))
            FROM FeedBack fb
            JOIN fb.foodMenu fm
            JOIN fm.food f
            WHERE fb.createdAt BETWEEN :startDate AND :endDate
            GROUP BY f.category
        """)
    List<CategoryScoreAvgDto> getAvgScoreByCategory(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query("""
            SELECT fb
            FROM FeedBack fb
            JOIN FETCH fb.foodMenu fm
            JOIN FETCH fm.menu m
            WHERE m IN :menuList
        """)
    List<FeedBack> getFeedBackByMenu(@Param("menuList") List<Menu> menuList);

}