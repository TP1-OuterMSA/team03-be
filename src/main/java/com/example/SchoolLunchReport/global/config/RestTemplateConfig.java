package com.example.SchoolLunchReport.global.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate aiReportRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);

        restTemplate.getMessageConverters().clear();
        restTemplate.getMessageConverters().add(converter);
        restTemplate.getMessageConverters().add(stringConverter);

        return restTemplate;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}