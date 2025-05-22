package com.example.SchoolLunchReport.suggestion.repository;

import com.example.SchoolLunchReport.suggestion.domain.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionJpaRepo extends JpaRepository<Suggestion, Long> {

}
