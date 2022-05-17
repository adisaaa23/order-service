package id.saputra.adi.orderservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Slf4j
@Configuration
public class RestTemplateConfig {
    @Value(value = "${rest.client.connect.timeout:3}")
    private int restClientConnectTimeout;
    @Value(value = "${rest.client.read.timeout:5}")
    private int restClientReadTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestTemplate restTemplate = restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(restClientReadTimeout))
                .setConnectTimeout(Duration.ofSeconds(restClientConnectTimeout))
                .build();
        restTemplate.setRequestFactory(factory);
        restTemplate.setInterceptors(Collections.emptyList());
        return restTemplate;
    }
}

