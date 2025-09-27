package com.developer.superuser.paymentservice.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "tokenservice.api")
@Data
public class TokenSvcConfigProperties {
    private String baseUrl;
    private Map<String, String> endpoint;
}