package com.developer.superuser.virtualaccountservice.core.config;

import com.developer.superuser.virtualaccountservice.core.property.DokuConfigProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class RestClientConfig {
    @Bean
    public RestClient dokuRestClient(ObjectMapper mapper, DokuConfigProperties dokuConfig) {
        return buildRestClient(mapper, dokuConfig.getApi().getBaseUrl(), "DokuRestClient");
    }

    private RestClient buildRestClient(ObjectMapper mapper, String baseUrl, String beanName) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(((request, body, execution) -> {
                    log.info("{} request URI --- {}", beanName, request.getURI());
                    log.info("{} request method --- {}", beanName, request.getMethod());
                    log.info("{} request headers --- {}", beanName, request.getHeaders());
                    log.info("{} request body --- {}", beanName, new String(body, StandardCharsets.UTF_8));
                    return execution.execute(request, body);
                }))
                .messageConverters(converters -> {
                    converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
                    converters.add(new MappingJackson2HttpMessageConverter(mapper));
                })
                .build();
    }
}