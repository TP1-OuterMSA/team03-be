package com.example.SchoolLunchReport.product.menu.repository;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
}