package com.example.SchoolLunchReport.domain.product.food.repository;

import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodJpaRepository extends JpaRepository<Food, Long> {

    List<Food> findByCategory(Category category);

    Optional<Food> findByName(String foodName);

    @Query(value = """
        SELECT f.category, f.sub_category, COUNT(f.sub_category)
        FROM food_menu fm
        JOIN food f ON fm.food_id = f.id
        JOIN menu m ON fm.menu_id = m.id
        WHERE m.date BETWEEN :startDate AND :endDate
        GROUP BY f.category, f.sub_category
        """, nativeQuery = true)
    List<Object[]> countFoodByCategory(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    boolean existsByName(String name);
}