package com.example.SchoolLunchReport.domain.statistics.domain.desired.repo;

import com.example.SchoolLunchReport.domain.statistics.domain.desired.entity.DesiredFood;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DesiredFoodJpaRepo extends JpaRepository<DesiredFood, Long> {

    @Query(value = """
        SELECT df.food_name, Count(df.food_name) 
        from paper1.desired_food  df
        WHERE df.created_at BETWEEN :startDate and :endDate
        group by df.food_name
        order by (count(df.food_name)) desc
        LIMIt :n
        """, nativeQuery = true)
    List<Object[]> findFoodNamesTopN(
        @Param(value = "startDate") LocalDate startDate,
        @Param(value = "endDate") LocalDate endDate,
        @Param(value = "n") int n);
}
