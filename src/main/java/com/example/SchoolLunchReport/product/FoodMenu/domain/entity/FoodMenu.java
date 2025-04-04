package com.example.SchoolLunchReport.product.FoodMenu.domain.entity;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Menu menu;
    @ManyToOne
    private Food food;
}