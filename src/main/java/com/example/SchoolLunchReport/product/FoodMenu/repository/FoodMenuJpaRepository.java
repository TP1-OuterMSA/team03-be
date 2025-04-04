package com.example.SchoolLunchReport.product.FoodMenu.repository;
import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FoodMenuJpaRepository extends JpaRepository<FoodMenu, Long> {
}