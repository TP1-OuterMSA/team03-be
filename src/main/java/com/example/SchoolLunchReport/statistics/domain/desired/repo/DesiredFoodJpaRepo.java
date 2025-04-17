package com.example.SchoolLunchReport.statistics.domain.desired.repo;

import com.example.SchoolLunchReport.statistics.domain.desired.entity.DesiredFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesiredFoodJpaRepo extends JpaRepository<DesiredFood, Long>,
    DesiredFoodRepoCustom {

}
