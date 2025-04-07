package com.example.SchoolLunchReport.statistics.repository;

import com.example.SchoolLunchReport.statistics.domain.entity.FeedBack;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackJpaRepo extends JpaRepository<FeedBack, Long> {

    List<FeedBack> findByCreatedAtBetween(LocalDate createdAt, LocalDate createdAt2);

    boolean existsByFoodMenuId(Long foodMenuId);
}