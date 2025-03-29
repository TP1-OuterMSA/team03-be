package com.example.SchoolLunchReport.statistics.feedback.repo;

import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackJpaRepo extends JpaRepository<FeedBack, Long> {

    List<FeedBack> findByCreatedAtAfter(LocalDate periodEndDate);

}
