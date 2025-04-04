package com.example.SchoolLunchReport.product.food.repository;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.domain.type.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FoodJpaRepository extends JpaRepository<Food, Long> {
    List<Food> findByCategory(Category category);
}