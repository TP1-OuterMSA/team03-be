package com.example.SchoolLunchReport.domain.statistics.domain.feedback.consumer;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.FoodMenu.support.FoodMenuReader;
import com.example.SchoolLunchReport.domain.product.evaluation.entity.Evaluation;
import com.example.SchoolLunchReport.domain.product.evaluation.repository.EvaluationJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.support.FoodReader;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import com.example.SchoolLunchReport.domain.product.menu.support.MenuReader;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.entity.FeedBack;
import com.example.SchoolLunchReport.domain.statistics.domain.feedback.repo.FeedBackJpaRepo;
import com.example.kafka_schemas.ReviewEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewEventConsumer {

    final FeedBackJpaRepo feedBackJpaRepo;
    final MenuReader menuReader;
    final FoodReader foodReader;
    final FoodMenuReader foodMenuReader;
    final EvaluationJpaRepository evaluationJpaRepository;

    @KafkaListener(
        topics = "review.events",
        groupId = "review-group",
        containerFactory = "kafkaListenerContainerReviewFactory" // 수동 커밋을 위한 팩토리 지정
    )
    @Transactional
    public void consume(ReviewEvent reviewEvent, Acknowledgment ack) {
        // 피드백을 하면 그걸 저장한다.
        try {
            String foodMenuDate = reviewEvent.getMealdayInfo();
            LocalDate menuDate = LocalDate.parse(foodMenuDate);
            MealType mealType = MealType.getInstance(reviewEvent.getMealType());
            log.info("mealType{} {}", mealType, reviewEvent.getMealType());
            Menu menu = menuReader.getMenuByDateAndType(menuDate, mealType);

            Evaluation evaluation = Evaluation
                .builder()
                .evaluation(reviewEvent.getOverallOpinion())
                .menu(menu)
                .build();

            List<FeedBack> feedBackList = new ArrayList<>();

            Map<String, String> menuAnswers = reviewEvent.getMenuAnswers();
            for (Entry<String, Integer> entry : reviewEvent.getMenuRatings().entrySet()) {
                String foodName = entry.getKey();
                Food food = foodReader.findByFoodName(foodName);
                FoodMenu foodMenu = foodMenuReader.getByMenuAndFood(menu, food);
                FeedBack feedBack = FeedBack.builder()
                    .score((double) entry.getValue())
                    .evaluation(menuAnswers.get(entry.getKey()))
                    .foodMenu(foodMenu)
                    .build();
                feedBackList.add(feedBack);
            }
            feedBackJpaRepo.saveAll(feedBackList);
            evaluationJpaRepository.save(evaluation);
            log.info("으ㅏㅏㅏㅏ 받았다." + reviewEvent.getMealType());
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Kafka 처리 중 에러 발생: {}", e.getMessage(), e);
            // 커밋하지 않음 → 메시지는 다시 처리됨
            throw new RuntimeException("Kafka 처리 실패", e);
        }
    }
}
