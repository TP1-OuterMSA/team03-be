package com.example.SchoolLunchReport.product.menu.service;

import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.product.menu.repository.MenuJpaRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    final MenuJpaRepository menuJpaRepository;

    public List<Menu> getMenuInBoundary(LocalDate startDate, LocalDate endDate) {
        return menuJpaRepository.findByDateBetween(startDate, endDate);
    }
}
