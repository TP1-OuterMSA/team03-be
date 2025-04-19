package com.example.SchoolLunchReport.product.FoodMenu.repository;
import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FoodMenuJpaRepository extends JpaRepository<FoodMenu, Long> {
    List<FoodMenu> findByMenu(Menu menu);
}