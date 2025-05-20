package com.example.SchoolLunchReport.global.config;

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
    public ConcurrentKafkaListenerContainerFactory<String, ReviewEvent> kafkaListenerContainerFactory(
        ConsumerFactory<String, ReviewEvent> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, ReviewEvent> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // 중요!!
        return factory;
    }
}
