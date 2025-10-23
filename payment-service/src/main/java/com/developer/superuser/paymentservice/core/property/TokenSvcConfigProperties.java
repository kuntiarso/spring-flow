package com.developer.superuser.paymentservice.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "token-service.api")
public record TokenSvcConfigProperties(
        String baseUrl,
        Map<String, String> endpoint
) {
}