package com.developer.superuser.orchestratorservice.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "payment-service.api")
public record PaymentServiceConfigMap(
        String baseUrl,
        Map<String, String> endpoint
) {
}