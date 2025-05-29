package com.example.SchoolLunchReport.domain.product.menu.consumer;

import com.example.SchoolLunchReport.domain.product.FoodMenu.domain.entity.FoodMenu;
import com.example.SchoolLunchReport.domain.product.FoodMenu.repository.FoodMenuJpaRepository;
import com.example.SchoolLunchReport.domain.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.domain.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.domain.product.menu.domain.entity.Menu;
import com.example.SchoolLunchReport.domain.product.menu.domain.type.MealType;
import com.example.SchoolLunchReport.domain.product.menu.repository.MenuJpaRepository;
import com.example.kafka_schemas.MealEvent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuEventConsumer {

    private final MenuJpaRepository menuRepository;
    private final FoodJpaRepository foodRepository;
    private final FoodMenuJpaRepository foodMenuRepository;

    @KafkaListener(topics = "meal.web.crawler.updated", groupId = "web-Crawler2",
        containerFactory = "kafkaListenerContainerMenuFactory")
    @Transactional
    public void consume(MealEvent event, Acknowledgment ack) {
        MealType mealType = MealType.getInstance(event.getMealType());
        LocalDate date = LocalDate.parse(event.getDate());
        List<String> foodNames = Arrays.asList(event.getMealContents().split(" "));

        Menu menu = Menu.builder()
            .date(date)
            .mealType(mealType)
            .build();
        menuRepository.save(menu);

        for (String foodName : foodNames) {
            log.info("meal event 발생 >> 음식 이름: {}", foodNames);
            Optional<Food> optionFood = foodRepository.findByName(foodName);
            if (optionFood.isEmpty()) {
                try {
                    foodRepository.save(Food.builder().name(foodName).build());
                    optionFood = foodRepository.findByName(foodName);
                } catch (DataIntegrityViolationException e) {
                    throw new IllegalStateException("음식 저장 중 충돌 발생");
                } finally {
                    ack.acknowledge();
                }
            }

            FoodMenu foodMenu = FoodMenu.builder()
                .food(optionFood.get())
                .menu(menu)
                .build();
            foodMenuRepository.save(foodMenu);
        }
        ack.acknowledge();
    }
}