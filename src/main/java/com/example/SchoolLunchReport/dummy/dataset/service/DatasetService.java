package com.example.SchoolLunchReport.dummy.dataset.service;

import com.example.SchoolLunchReport.client.LLMClient;
import com.example.SchoolLunchReport.client.dto.request.AdjustMenuNameRequestDto;
import com.example.SchoolLunchReport.dummy.dataset.dto.FeedBackDTO;
import com.example.SchoolLunchReport.dummy.dataset.dto.MenuWithFoodsAndEvaluationDTO;
import com.example.SchoolLunchReport.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.product.evaluation.entity.Evaluation;
import com.example.SchoolLunchReport.product.evaluation.repository.EvaluationJpaRepository;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.domain.type.Category;
import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.product.menu.repository.MenuJpaRepository;
import com.example.SchoolLunchReport.statistics.domain.desired.entity.DesiredFood;
import com.example.SchoolLunchReport.statistics.domain.desired.repo.DesiredFoodJpaRepo;
import com.example.SchoolLunchReport.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.domain.feedback.repo.FeedBackJpaRepo;
import com.example.SchoolLunchReport.statistics.domain.rank.support.RankImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DatasetService {

    private final FoodJpaRepository foodJpaRepository;
    private final FoodMenuJpaRepository foodMenuJpaRepository;
    private final MenuJpaRepository menuJpaRepository;
    private final FeedBackJpaRepo feedBackJpaRepo;
    private final LLMClient llmClient;
    private final DesiredFoodJpaRepo desiredFoodJpaRepo;
    private final EvaluationJpaRepository evaluationJpaRepository;
    private final RankImpl rankImpl;

    @Transactional
    public int createFoodData(List<Food> foods) {
        List<Food> savedFoods = foodJpaRepository.saveAll(foods);
        return savedFoods.size();
    }

    @Transactional
    public int createMenuWithFoodsAndEvaluation(
        List<MenuWithFoodsAndEvaluationDTO> menuWithFoodsAndEvaluationList) {
        List<Menu> menus = new ArrayList<>();
        List<FoodMenu> foodMenus = new ArrayList<>();
        List<Evaluation> evaluations = new ArrayList<>();
        Map<Category, List<Food>> foodsByCategory = new HashMap<>();
        for (Category category : Category.values()) {
            foodsByCategory.put(category, foodJpaRepository.findByCategory(category));
        }
        for (Category category : Category.values()) {
            if (foodsByCategory.get(category).isEmpty()) {
                throw new IllegalStateException(
                    category + " 카테고리에 음식이 없습니다. 모든 카테고리에 최소 한 개 이상의 음식을 생성해주세요.");
            }
        }
        for (MenuWithFoodsAndEvaluationDTO dto : menuWithFoodsAndEvaluationList) {
            Menu menu = new Menu();
            setField(menu, "date", dto.getDate());
            setField(menu, "mealType", dto.getMealType());
            Menu savedMenu = menuJpaRepository.save(menu);
            if (dto.getEvaluation() != null && !dto.getEvaluation().trim().isEmpty()) {
                Evaluation evaluation = new Evaluation();
                evaluation.setMenu(savedMenu);
                evaluation.setEvaluation(dto.getEvaluation());
                evaluations.add(evaluation);
            }
            for (Category category : Category.values()) {
                List<Food> foodsInCategory = foodsByCategory.get(category);
                Food selectedFood = foodsInCategory.get(
                    new Random().nextInt(foodsInCategory.size()));
                FoodMenu foodMenu = new FoodMenu();
                foodMenu.setMenu(savedMenu);
                foodMenu.setFood(selectedFood);
                foodMenus.add(foodMenu);
            }
        }
        foodMenuJpaRepository.saveAll(foodMenus);
        if (!evaluations.isEmpty()) {
            evaluationJpaRepository.saveAll(evaluations);
        }
        return menuWithFoodsAndEvaluationList.size();
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
                    .orElseThrow(() -> new IllegalArgumentException(
                            "ID가 " + dto.getFoodMenuId() + "인 푸드메뉴를 찾을 수 없습니다"));
            FeedBack feedBack = FeedBack.builder()
                    .score(dto.getScore())
                    .foodMenu(foodMenu)
                    .evaluation(dto.getEvaluation())
                    .build();
            feedBackList.add(feedBack);
        }
        feedBackJpaRepo.saveAll(feedBackList);
        return feedBackList.size();
    }

    @Transactional
    public int createRandomFeedback(LocalDate registerDate) {
        List<FoodMenu> allFoodMenus = foodMenuJpaRepository.findAll();
        List<FeedBack> feedBackList = new ArrayList<>();
        Random random = new Random();
        for (FoodMenu foodMenu : allFoodMenus) {
            if (feedBackJpaRepo.existsByFoodMenuId(foodMenu.getId())) {
                continue;
            }
            double randomScore = random.nextInt(5) + 1;
            FeedBack feedBack = FeedBack.builder()
                .score(randomScore)
                .foodMenu(foodMenu)
                .createdAt(registerDate)
                .build();
            feedBackList.add(feedBack);
        }
        feedBackJpaRepo.saveAll(feedBackList);
        return feedBackList.size();
    }

    @Transactional(readOnly = true)
    public List<Menu> getAllMenus() {
        return menuJpaRepository.findAll();
    }

    @Transactional
    public List<DesiredFood> createDesiredFoods() {
        List<String> desiredFoodData = List.of(
            // 제육볶음 변형 12개
            "제육볶음", "제육보끔", "제육뽁음", "제육볶음!", "제육볶음2", "제육_bokkeum",
            "제육볶음!!", "제육볶음55", "제육볽음", "jeyukbokkeum", "제욱볶음", "제육٥٦",

            // 김치찌개 변형 12개
            "김치찌개", "김치찌깨", "김치찌개?", "김치찌개1", "김치지개", "ㅣ김치찌개",
            "김치찌개!!", "Kimchi jjigae", "김치찌개99", "김치치개", "김치ㅉ개", "김치 찌개",

            // 불고기 변형 12개
            "불고기", "불꼬기", "불고기?", "불고기123", "불고1", "bulgogi",
            "불고기!!", "불고기★", "불고기88", "불고기맛", "bulgogi66", "불고기찌",

            // 비빔밥 변형 6개
            "비빔밥", "비빔박", "비빔밥!", "비빔밥2", "ㅂㅣ빔밥", "bibimbap",

            // 떡볶이 변형 6개
            "떡볶이", "떡뽁이", "떡볶이?", "떡볶이3", "떡뽁이!", "ddeokbokki",

            // 순두부찌개 변형 6개
            "순두부찌개", "순두부지개", "순두부찌개@", "순두부찌개4", "ㅅㅜㄴ두부찌개", "sundubujjigae",

            // 칼국수 변형 6개
            "칼국수", "칼꾹수", "칼국수?", "칼국수5", "ㅋㄹ국수", "kalguksu",

            // 김밥 변형 6개
            "김밥", "김밥!", "김박", "김밥6", "ㄱㅣ밥", "gimbap",

            // 라면 변형 6개
            "라면", "라민", "라면?", "라면7", "ㄹㅏ면", "ramen"
        );
        List<DesiredFood> desiredFoodList = new ArrayList<>();
        for (String desiredFood : desiredFoodData) {
            String foodName = llmClient.adjustMenuApi(AdjustMenuNameRequestDto.from(desiredFood));
            desiredFoodList.add(new DesiredFood(foodName));
        }
        desiredFoodJpaRepo.saveAll(desiredFoodList);
        return desiredFoodList;
    }
}
