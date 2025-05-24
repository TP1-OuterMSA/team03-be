package com.example.SchoolLunchReport.domain.statistics.domain.desired.repo;

import com.example.SchoolLunchReport.domain.statistics.domain.desired.entity.DesiredFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesiredFoodJpaRepo extends JpaRepository<DesiredFood, Long>,
    DesiredFoodRepoCustom {

}
