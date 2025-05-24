package com.example.SchoolLunchReport.domain.suggestion.repository;

import com.example.SchoolLunchReport.domain.suggestion.domain.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionJpaRepo extends JpaRepository<Suggestion, Long> {

}
