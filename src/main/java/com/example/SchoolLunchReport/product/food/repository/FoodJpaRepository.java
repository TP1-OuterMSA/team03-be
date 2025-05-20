package com.example.SchoolLunchReport.product.food.repository;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.domain.type.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodJpaRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {

    List<Food> findByCategory(Category category);
    
    Optional<Food> findByName(String foodName);
}