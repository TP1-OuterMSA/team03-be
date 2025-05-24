package com.example.SchoolLunchReport.domain.product.menu.support;

import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import com.example.SchoolLunchReport.domain.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.domain.statistics.domain.boundary.entity.Boundary;
import java.time.LocalDate;
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

    public Menu getMenuByDateAndType(LocalDate menuDate, MealType mealType) {
        return menuJpaRepository.findByDateAndMealType(menuDate, mealType)
            .orElseThrow(
                () -> new IllegalArgumentException(
                    String.format("존재하지 않은 메뉴입니다.\nmenuDate: %s mealType: %s", menuDate,
                        mealType.name())));
    }
}
