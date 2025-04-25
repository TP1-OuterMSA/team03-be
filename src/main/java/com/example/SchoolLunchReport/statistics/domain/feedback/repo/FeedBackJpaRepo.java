package com.example.SchoolLunchReport.statistics.domain.feedback.repo;

import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackJpaRepo extends JpaRepository<FeedBack, Long>, FeedbackRepositoryCustom {

    List<FeedBack> findByCreatedAtBetween(LocalDate createdAt, LocalDate createdAt2);

    boolean existsByFoodMenuId(Long foodMenuId);

    List<FeedBack> findByCreatedAt(LocalDate startDate);

    List<FeedBack> findByFoodMenu(FoodMenu foodMenu);
}