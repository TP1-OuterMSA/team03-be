package com.example.SchoolLunchReport.aichatbot.util;
import org.apache.commons.text.similarity.CosineSimilarity;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringSimilarityUtil {
    private static final String TEXT_DELIMITER = " ";
    private static final CosineSimilarity cosineSimilarity = new CosineSimilarity();

    public static double calculateSimilarity(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0.0;
        }

        Map<CharSequence, Integer> vector1 = Arrays.stream(text1.split(TEXT_DELIMITER))
                .collect(Collectors.toMap(character -> character, character -> 1, Integer::sum));

        Map<CharSequence, Integer> vector2 = Arrays.stream(text2.split(TEXT_DELIMITER))
                .collect(Collectors.toMap(character -> character, character -> 1, Integer::sum));

        return cosineSimilarity.cosineSimilarity(vector1, vector2);
    }
}