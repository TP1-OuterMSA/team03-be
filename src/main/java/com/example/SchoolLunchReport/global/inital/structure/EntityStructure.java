package com.example.SchoolLunchReport.global.inital.structure;

import com.example.SchoolLunchReport.product.food.domain.entity.Food;
import com.example.SchoolLunchReport.product.food.domain.type.Category;
import com.example.SchoolLunchReport.product.food.repository.FoodJpaRepository;
import com.example.SchoolLunchReport.statistics.feedback.domain.entity.FeedBack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityStructure {

    private final FoodJpaRepository foodJpaRepository;

    private static final int FOOD_INFO_COUNT = 3;
    private static final int FEED_BACK_INFO_COUNT = 3;
    private static final int column = 1;

    public List<String> readFile(String filePath) throws IOException {
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream();
            BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("에러");
        }
    }

    public Set<Food> extractFood(String filePath) throws IOException { //List<String> data
        List<String> data = readFile(filePath);
        return data.stream()
            .skip(column)
            .map(this::convertToFood)
            .collect(Collectors.toSet());
    }

    public Set<FeedBack> extractFeedBack(String filePath) throws IOException {
        List<String> data = readFile(filePath);
        return data.stream()
            .skip(column)
            .map(this::convertToFeedBack)
            .collect(Collectors.toSet());
    }

    private FeedBack convertToFeedBack(String line) {
        String[] feedBackInfo = splitItems(line);
        if (feedBackInfo.length < FEED_BACK_INFO_COUNT) {
//            throw new Exception("파일 읽기 실패");
        }
        Food food = foodJpaRepository.findById(Long.parseLong(feedBackInfo[2])).get();

        return FeedBack
            .builder()
            .id(Long.parseLong(feedBackInfo[0]))
            .score(Double.parseDouble(feedBackInfo[1]))
            .food(food)
            .createdAt(LocalDate.now())
            .build();
    }

    private Food convertToFood(String line) {
        String[] foodInfo = splitItems(line);
        if (foodInfo.length < FOOD_INFO_COUNT) {
//            throw new Exception("파일 읽기 실패");
        }
        return Food
            .builder()
            .id(Long.parseLong(foodInfo[0]))
            .foodName(foodInfo[1])
            .category(Category.getInstance(foodInfo[2]))
            .foodRank(Integer.parseInt(foodInfo[3]))
            .build();
    }

    private String[] splitItems(String input) {
        return Arrays.stream(input.split(","))
            .map(String::trim)
            .toArray(String[]::new);
    }
}
