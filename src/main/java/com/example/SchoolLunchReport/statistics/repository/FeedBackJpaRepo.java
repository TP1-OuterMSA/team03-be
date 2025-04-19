package com.example.SchoolLunchReport.statistics.repository;

import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackJpaRepo extends JpaRepository<FeedBack, Long> {
    List<FeedBack> findByCreatedAtBetween(LocalDate createdAt, LocalDate createdAt2);
    boolean existsByFoodMenuId(Long foodMenuId);
    List<FeedBack> findByCreatedAt(LocalDate startDate);
    // 특정 FoodMenu의 모든 피드백 조회
    List<FeedBack> findByFoodMenu(FoodMenu foodMenu);
}