package com.developer.superuser.tokenservice.core.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "doku")
public record DokuConfigProperties(
        Merchant merchant,
        Api api
) {
    public record Merchant(
            String clientId,
            KeyStore keyStore,
            PrivateKey privateKey
    ) {
    }

    public record KeyStore(
            String location,
            String alias,
            String password
    ) {
    }

    public record PrivateKey(
            String passphrase
    ) {
    }

    public record Api(
            String key,
            String baseUrl,
            Map<String, String> endpoint
    ) {
    }
}