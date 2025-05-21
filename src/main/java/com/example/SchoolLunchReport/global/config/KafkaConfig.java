package com.example.SchoolLunchReport.global.config;

import com.example.kafka_schemas.MealEvent;
import com.example.kafka_schemas.ReviewEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReviewEvent> kafkaListenerContainerReviewFactory(
        ConsumerFactory<String, ReviewEvent> reviewConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, ReviewEvent> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(reviewConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // 중요!!
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MealEvent> kafkaListenerContainerMenuFactory(
        ConsumerFactory<String, MealEvent> mealConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, MealEvent> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(mealConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // 중요!!
        return factory;
    }

}
