package com.example.SchoolLunchReport.product.menu.service;

import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.product.menu.support.MenuReader;
import com.example.SchoolLunchReport.statistics.domain.boundary.entity.Boundary;
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
}
