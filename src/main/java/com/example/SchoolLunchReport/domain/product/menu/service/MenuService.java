package com.example.SchoolLunchReport.domain.product.menu.service;

import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import com.example.SchoolLunchReport.domain.product.menu.support.MenuReader;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    final MenuReader menuReader;

    public List<Menu> getMenuInBoundary(Boundary boundary) {
        return menuReader.getMenuInBoundary(boundary);
    }

    public List<Menu> getMenuInBoundary(LocalDate startDate, LocalDate endDate) {
        return menuReader.getMenuInBoundary(startDate, endDate);
    }

    public boolean isRegisterMenu(LocalDate date, MealType mealType) {
        return menuReader.findMenuByDateAndType(date, mealType).isPresent();
    }
    
}
