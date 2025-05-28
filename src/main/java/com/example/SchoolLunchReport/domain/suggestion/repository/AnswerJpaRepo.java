package com.example.SchoolLunchReport.domain.suggestion.repository;

import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerJpaRepo extends JpaRepository<Answer, Long> {

}
