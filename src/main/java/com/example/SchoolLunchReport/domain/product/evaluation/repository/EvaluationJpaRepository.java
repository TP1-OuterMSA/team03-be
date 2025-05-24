package com.example.SchoolLunchReport.domain.product.evaluation.repository;
import com.example.SchoolLunchReport.domain.product.evaluation.entity.Evaluation;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EvaluationJpaRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByMenu(Menu menu);
}