package com.example.SchoolLunchReport.statistics.feedback.repository;
import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FeedBackJpaRepo extends JpaRepository<FeedBack, Long> {
    List<FeedBack> findByCreatedAtAfter(LocalDate periodEndDate);
    boolean existsByFoodMenuId(Long foodMenuId);
}