package com.developer.superuser.virtualaccountservice.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "doku")
@Data
public class DokuConfigProperties {
    private Api api;

    @Data
    public static class Api {
        private String key;
        private String baseUrl;
        private Map<String, String> endpoint;
    }
}