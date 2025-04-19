package com.example.SchoolLunchReport.product.menu.repository;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
    // 시작일~종료일 사이의 모든 메뉴 조회
    List<Menu> findByDateBetween(LocalDate startDate, LocalDate endDate);
}