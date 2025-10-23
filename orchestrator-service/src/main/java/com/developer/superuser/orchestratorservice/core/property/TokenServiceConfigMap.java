package com.developer.superuser.orchestratorservice.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "token-service.api")
public record TokenServiceConfigMap(
        String baseUrl,
        Map<String, String> endpoint
) {
}