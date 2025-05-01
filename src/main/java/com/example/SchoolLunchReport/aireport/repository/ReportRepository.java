package com.example.SchoolLunchReport.aireport.repository;
import com.example.SchoolLunchReport.aireport.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}