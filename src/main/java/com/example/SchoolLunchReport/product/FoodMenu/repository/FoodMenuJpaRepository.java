package com.example.SchoolLunchReport.product.FoodMenu.repository;

import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMenuJpaRepository extends JpaRepository<FoodMenu, Long> {

    List<FoodMenu> findByMenu(Menu menu);

    Optional<FoodMenu> findByMenuAndFood(Menu menu, Food food);
}