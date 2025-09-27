package com.developer.superuser.paymentservice.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "vaservice.api")
@Data
public class VaSvcConfigProperties {
    private String baseUrl;
    private Map<String, String> endpoint;
}