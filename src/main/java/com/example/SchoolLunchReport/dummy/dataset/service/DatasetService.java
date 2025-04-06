package com.example.SchoolLunchReport.dummy.dataset.service;
import com.example.SchoolLunchReport.dummy.dataset.dto.FeedBackDTO;
import com.example.SchoolLunchReport.dummy.dataset.dto.MenuWithFoodsDTO;
import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.domain.type.Category;
import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.feedback.repository.FeedBackJpaRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class DatasetService {
    private final FoodJpaRepository foodJpaRepository;
    private final FoodMenuJpaRepository foodMenuJpaRepository;
    private final MenuJpaRepository menuJpaRepository;
    private final FeedBackJpaRepo feedBackJpaRepo;
    public DatasetService(FoodJpaRepository foodJpaRepository,
                          FoodMenuJpaRepository foodMenuJpaRepository,
                          MenuJpaRepository menuJpaRepository,
                          FeedBackJpaRepo feedBackJpaRepo) {
        this.foodJpaRepository = foodJpaRepository;
        this.foodMenuJpaRepository = foodMenuJpaRepository;
        this.menuJpaRepository = menuJpaRepository;
        this.feedBackJpaRepo = feedBackJpaRepo;
    }
    @Transactional
    public int createFoodData(List<Food> foods) {
        List<Food> savedFoods = foodJpaRepository.saveAll(foods);
        return savedFoods.size();
    }
    @Transactional
    public int createMenuWithFoods(List<MenuWithFoodsDTO> menuWithFoodsList) {
        List<Menu> menus = new ArrayList<>();
        List<FoodMenu> foodMenus = new ArrayList<>();
        Map<Category, List<Food>> foodsByCategory = new HashMap<>();
        for (Category category : Category.values()) {
            foodsByCategory.put(category, foodJpaRepository.findByCategory(category));
        }
        for (Category category : Category.values()) {
            if (foodsByCategory.get(category).isEmpty()) {
                throw new IllegalStateException(category + " 카테고리에 음식이 없습니다. 모든 카테고리에 최소 한 개 이상의 음식을 생성해주세요.");
            }
        }
        for (MenuWithFoodsDTO dto : menuWithFoodsList) {
            Menu menu = new Menu();
            setField(menu, "date", dto.getDate());
            setField(menu, "mealType", dto.getMealType());
            setField(menu, "evaluation", dto.getEvaluation());

            Menu savedMenu = menuJpaRepository.save(menu);
            for (Category category : Category.values()) {
                List<Food> foodsInCategory = foodsByCategory.get(category);
                Food selectedFood = foodsInCategory.get(new Random().nextInt(foodsInCategory.size()));
                FoodMenu foodMenu = new FoodMenu();
                foodMenu.setMenu(savedMenu);
                foodMenu.setFood(selectedFood);
                foodMenus.add(foodMenu);
            }
        }
        foodMenuJpaRepository.saveAll(foodMenus);
        return menuWithFoodsList.size();
    }
    private void setField(Object object, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException("필드 " + fieldName + " 설정 실패", e);
        }
    }
    @Transactional
    public int createFeedback(List<FeedBackDTO> feedbacks) {
        List<FeedBack> feedBackList = new ArrayList<>();
        for (FeedBackDTO dto : feedbacks) {
            if (dto.getScore() < 1 || dto.getScore() > 5) {
                throw new IllegalArgumentException("점수는 1~5 사이의 값이어야 합니다: " + dto.getScore());
            }
            FoodMenu foodMenu = foodMenuJpaRepository.findById(dto.getFoodMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("ID가 " + dto.getFoodMenuId() + "인 푸드메뉴를 찾을 수 없습니다"));
            if (feedBackJpaRepo.existsByFoodMenuId(dto.getFoodMenuId())) continue;
            FeedBack feedBack = FeedBack.builder()
                    .score((double) dto.getScore())
                    .build();
            setField(feedBack, "foodMenu", foodMenu);
            feedBackList.add(feedBack);
        }
        feedBackJpaRepo.saveAll(feedBackList);
        return feedBackList.size();
    }
    @Transactional
    public int createRandomFeedback() {
        List<FoodMenu> allFoodMenus = foodMenuJpaRepository.findAll();
        List<FeedBack> feedBackList = new ArrayList<>();
        Random random = new Random();
        for (FoodMenu foodMenu : allFoodMenus) {
            if (feedBackJpaRepo.existsByFoodMenuId(foodMenu.getId())) {
                continue;
            }
            double randomScore = random.nextInt(5) + 1.0;
            FeedBack feedBack = FeedBack.builder()
                    .score(randomScore)
                    .build();
            setField(feedBack, "foodMenu", foodMenu);
            feedBackList.add(feedBack);
        }
        feedBackJpaRepo.saveAll(feedBackList);
        return feedBackList.size();
    }
    @Transactional(readOnly = true)
    public List<Menu> getAllMenus() {
        return menuJpaRepository.findAll();
    }
}