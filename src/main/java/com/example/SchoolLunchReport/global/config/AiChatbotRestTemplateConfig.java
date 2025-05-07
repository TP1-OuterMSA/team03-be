package com.example.SchoolLunchReport.global.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
@Configuration
public class AiChatbotRestTemplateConfig {
    @Bean(name = "chatbotRestTemplate")
    public RestTemplate chatbotRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(50000);
        factory.setReadTimeout(50000);

        RestTemplate restTemplate = new RestTemplate(factory);

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(
                Arrays.asList(
                        MediaType.APPLICATION_JSON,
                        MediaType.APPLICATION_JSON_UTF8
                )
        );

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setWriteAcceptCharset(false);

        restTemplate.setMessageConverters(Arrays.asList(jsonConverter, stringConverter));

        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            System.out.println("요청 URL: " + request.getURI());
            System.out.println("요청 방식: " + request.getMethod());
            System.out.println("요청 본문: " + new String(body, StandardCharsets.UTF_8));
            return execution.execute(request, body);
        }));
        return restTemplate;
    }
}