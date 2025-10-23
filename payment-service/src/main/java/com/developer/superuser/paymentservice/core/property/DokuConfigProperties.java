package com.developer.superuser.paymentservice.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "doku")

public record DokuConfigProperties(
        Merchant merchant,
        Api api
) {
    public record Merchant(String clientId) {
    }

    public record Api(
            String key,
            String baseUrl,
            Map<String, String> endpoint
    ) {
    }
}