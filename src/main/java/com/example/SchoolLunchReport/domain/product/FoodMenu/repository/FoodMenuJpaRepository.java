package com.example.SchoolLunchReport.domain.product.FoodMenu.repository;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMenuJpaRepository extends JpaRepository<FoodMenu, Long> {

    List<FoodMenu> findByMenu(Menu menu);

    Optional<FoodMenu> findByMenuAndFood(Menu menu, Food food);

    List<FoodMenu> findByFoodId(Long foodId);

    void deleteAllByMenu(Menu menu);
}