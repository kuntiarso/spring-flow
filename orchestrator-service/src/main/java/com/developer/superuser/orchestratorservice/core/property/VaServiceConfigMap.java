package com.developer.superuser.orchestratorservice.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "va-service.api")
public record VaServiceConfigMap(
        String baseUrl,
        Map<String, String> endpoint
) {
}