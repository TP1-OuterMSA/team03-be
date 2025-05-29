package com.example.SchoolLunchReport.domain.product.service;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.domain.product.controller.dto.request.CreateMenuRequestDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.request.UpdateMenuRequestDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.CreateMenuResponseDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.DailyMenuResponseDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.FoodListResponseDto;
import com.example.SchoolLunchReport.domain.product.controller.dto.response.UpdateMenuResponseDto;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.domain.type.Category;
import com.example.SchoolLunchReport.domain.product.food.service.FoodService;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import com.example.SchoolLunchReport.domain.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.domain.product.menu.service.MenuService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.DuplicateResourceException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFacade {

    final FoodService foodService;
    final MenuService menuService;
    final FoodMenuJpaRepository foodMenuJpaRepository;
    final MenuJpaRepository menuJpaRepository;

    public Map<Category, List<FoodListResponseDto>> getMenuListByCategory() {
        List<Category> categoryOrder = List.of(
            Category.RICE,
            Category.MAIN_DISH,
            Category.SIDE_DISH,
            Category.DESSERT
        );

        List<Food> foodList = foodService.getFoodList();

        Map<Category, List<FoodListResponseDto>> grouped = foodList.stream()
            .collect(Collectors.groupingBy(
                Food::getCategory,
                Collectors.mapping(
                    f -> new FoodListResponseDto(f.getSubCategory(), f.getId(), f.getName()),
                    Collectors.toList()
                )
            ));

        Map<Category, List<FoodListResponseDto>> orderedMap = new LinkedHashMap<>();
        for (Category category : categoryOrder) {
            List<FoodListResponseDto> items = grouped.getOrDefault(category,
                Collections.emptyList());
            orderedMap.put(category, items);
        }
        return orderedMap;
    }

    public Map<LocalDate, DailyMenuResponseDto> getMenuListInBoundary(LocalDate startDate,
        LocalDate endDate) {
        List<Menu> menus = menuService.getMenuInBoundary(startDate, endDate);

        Map<LocalDate, DailyMenuResponseDto> result = new HashMap<>();
        Set<String> allergySet = new HashSet<>();
        for (Menu menu : menus) {
            Map<Category, String> typeMap = new EnumMap<>(Category.class);
            Set<String> hashtagSet = new HashSet<>();

            for (FoodMenu foodMenu : menu.getFoodMenuList()) {
                Food food = foodMenu.getFood();
                Category type = food.getCategory();

                typeMap.put(type, food.getName());
                allergySet.addAll(Arrays.stream(food.getAllergy().split("\\s*,\\s*"))
                    .toList());
                hashtagSet.add(menu.getHashTags());
            }
            allergySet.remove("없음");
            DailyMenuResponseDto dto = new DailyMenuResponseDto(
                menu.getId(),
                menu.getMealType(),
                typeMap.getOrDefault(Category.RICE, "없음"),
                typeMap.getOrDefault(Category.SOUP, "없음"),
                typeMap.getOrDefault(Category.MAIN_DISH, "없음"),
                typeMap.getOrDefault(Category.SIDE_DISH, "없음"),
                typeMap.getOrDefault(Category.DESSERT, "없음"),
                new ArrayList<>(allergySet),
                new ArrayList<>(hashtagSet),
                null
            );

            result.put(menu.getDate(), dto);
        }

        return result;
    }

    @Transactional
    public CreateMenuResponseDto createMenu(LocalDate date, MealType mealType,
        CreateMenuRequestDto dto) {
        if (menuService.isRegisterMenu(date, mealType)) {
            throw new DuplicateResourceException("이미 존재하는 메뉴입니다.");
        }
        Menu menu = Menu.builder()
            .date(date)
            .mealType(mealType)
            .hashTag(String.join(",", dto.hashtags()))
            .build();
        menuJpaRepository.save(menu);
        // 2. 각 카테고리별 음식 등록
        Set<String> allergySet = new HashSet<>();

        Map<Category, String> categoryToName = Map.of(
            Category.RICE, dto.rice(),
            Category.SOUP, dto.soup(),
            Category.MAIN_DISH, dto.mainDish(),
            Category.SIDE_DISH, dto.sideDish(),
            Category.DESSERT, dto.dessert()
        );

        for (Map.Entry<Category, String> entry : categoryToName.entrySet()) {
            String foodName = entry.getValue();
            Food food = foodService.getFoodByName(foodName);
            allergySet.addAll(Arrays.stream(food.getAllergy().split("\\s*,\\s*"))
                .toList());
            FoodMenu foodMenu = FoodMenu.builder()
                .menu(menu)
                .food(food)
                .build();
            foodMenuJpaRepository.save(foodMenu);
        }
        allergySet.remove("없음");
        return new CreateMenuResponseDto(menu.getId(), new ArrayList<>(allergySet));
    }

    @Transactional
    public UpdateMenuResponseDto updateMenu(Long menuId, UpdateMenuRequestDto dto) {
        // 1. 기존 메뉴 찾기
        Menu menu = menuJpaRepository.findById(menuId)
            .orElseThrow(() -> new ResourceNotFoundException("메뉴가 존재하지 않습니다."));

        // 2. 기존 FoodMenu 관계 전부 제거
        foodMenuJpaRepository.deleteAllByMenu(menu);

        // 3. 해시태그 갱신
        menu.updateHashtag(String.join(",", dto.hashtags()));

        // 4. 새 음식 등록
        Set<String> allergySet = new HashSet<>();
        Map<Category, String> categoryToName = Map.of(
            Category.RICE, dto.rice(),
            Category.SOUP, dto.soup(),
            Category.MAIN_DISH, dto.mainDish(),
            Category.SIDE_DISH, dto.sideDish(),
            Category.DESSERT, dto.dessert()
        );

        for (Map.Entry<Category, String> entry : categoryToName.entrySet()) {
            String foodName = entry.getValue();
            Food food = foodService.getFoodByName(foodName);
            allergySet.addAll(Arrays.stream(food.getAllergy().split("\\s*,\\s*"))
                .toList());
            FoodMenu foodMenu = FoodMenu.builder()
                .menu(menu)
                .food(food)
                .build();
            foodMenuJpaRepository.save(foodMenu);
        }

        allergySet.remove("없음");
        return new UpdateMenuResponseDto(menu.getId(), new ArrayList<>(allergySet));
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        menuJpaRepository.deleteById(menuId);
    }
}
