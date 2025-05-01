package com.example.SchoolLunchReport.product.menu.support;

import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuReader {

    final MenuJpaRepository menuJpaRepository;

    public List<Menu> getMenuInBoundary(Boundary boundary) {
        return menuJpaRepository.findByDateBetween(boundary.startDate(), boundary.endDate());
    }
}
