package com.example.SchoolLunchReport.global.inital;


import com.example.SchoolLunchReport.global.inital.structure.EntityStructure;
import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import com.example.SchoolLunchReport.statistics.feedback.repo.FeedBackJpaRepo;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EntityStructure entityStructure;
    private final FoodJpaRepository foodJpaRepository;
    private final FeedBackJpaRepo feedBackJpaRepo;

    @Override
    public void run(String... args) throws Exception {
        if (foodJpaRepository.count() == 0) {
            Set<Food> foods = entityStructure.extractFood("food.csv");
            foodJpaRepository.saveAll(foods);
        }
        if (feedBackJpaRepo.count() == 0) {
            Set<FeedBack> feedBacks = entityStructure.extractFeedBack("feedBack.csv");
            feedBackJpaRepo.saveAll(feedBacks);
        }
    }
}
