package com.example.SchoolLunchReport.ai.analyze.service;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodEvaluationRequestDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodInfoDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodScoreDto;
import com.example.SchoolLunchReport.ai.analyze.dto.FoodNameDto;
import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.FeedBackJpaRepo;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyzeService {

    private final FoodJpaRepository foodRepository;
    private final FoodMenuJpaRepository foodMenuRepository;
    private final FeedBackJpaRepo feedBackRepository;

    public AnalyzeService(FoodJpaRepository foodRepository, FoodMenuJpaRepository foodMenuRepository,
                          FeedBackJpaRepo feedBackRepository) {
        this.foodRepository = foodRepository;
        this.foodMenuRepository = foodMenuRepository;
        this.feedBackRepository = feedBackRepository;
    }

    public List<FoodNameDto> getAllFoodNames() {
        return foodRepository.findAll().stream()
                .map(food -> new FoodNameDto(food.getId(), food.getName()))
                .collect(Collectors.toList());
    }

    public FoodInfoDto getFoodInfo(Long foodId) {
        Food food = foodRepository.findById(foodId).orElse(null);
        if (food == null) {
            return null;
        }
        return new FoodInfoDto(
                food.getName(),
                food.getNutrition(),
                food.getCalorie(),
                food.getAllergy(),
                food.getCategory() != null ? food.getCategory().name() : "null",
                food.getSubCategory() != null ? food.getSubCategory().name() : "null"
        );
    }


    public int countFoodInMenu(Long foodId, LocalDate startDate, LocalDate endDate) {
        List<FoodMenu> foodMenus = foodMenuRepository.findByFoodId(foodId);
        return (int) foodMenus.stream()
                .filter(fm -> !fm.getMenu().getDate().isBefore(startDate) && !fm.getMenu().getDate().isAfter(endDate))
                .count();
    }

    public FoodScoreDto getFeedbackScores(Long foodId, LocalDate startDate, LocalDate endDate) {
        List<FoodMenu> foodMenus = foodMenuRepository.findByFoodId(foodId);

        List<Long> foodMenuIds = foodMenus.stream()
                .map(FoodMenu::getId)
                .collect(Collectors.toList());


        List<FeedBack> feedBacks = feedBackRepository.findByFoodMenuIdInAndCreatedAtBetween(
                foodMenuIds, startDate, endDate);

        List<Double> scores = feedBacks.stream()
                .sorted(Comparator.comparing(FeedBack::getCreatedAt))
                .map(FeedBack::getScore)
                .collect(Collectors.toList());

        System.out.println("최종 점수 목록: " + scores);

        return new FoodScoreDto(startDate, endDate, scores);
    }

    public FoodEvaluationRequestDto getFoodEvaluations(Long foodId, LocalDate startDate, LocalDate endDate) {
        Food food = foodRepository.findById(foodId).orElse(null);
        if (food == null) {
            return new FoodEvaluationRequestDto("알 수 없는 음식", new ArrayList<>());
        }

        List<FoodMenu> foodMenus = foodMenuRepository.findByFoodId(foodId);

        List<Long> foodMenuIds = foodMenus.stream()
                .map(FoodMenu::getId)
                .collect(Collectors.toList());

        List<FeedBack> feedBacks = feedBackRepository.findByFoodMenuIdInAndCreatedAtBetween(
                foodMenuIds, startDate, endDate);

        List<String> evaluations = feedBacks.stream()
                .filter(fb -> fb.getEvaluation() != null && !fb.getEvaluation().isEmpty())
                .map(FeedBack::getEvaluation)
                .collect(Collectors.toList());

        System.out.println("추출된 평가 텍스트 개수: " + evaluations.size());
        return new FoodEvaluationRequestDto(food.getName(), evaluations);
    }

}