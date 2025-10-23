package com.developer.superuser.orchestratorservice;

import com.developer.superuser.orchestratorservice.core.property.PaymentServiceConfigMap;
import com.developer.superuser.orchestratorservice.core.property.TokenServiceConfigMap;
import com.developer.superuser.orchestratorservice.core.property.VaServiceConfigMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
@EnableConfigurationProperties(value = {PaymentServiceConfigMap.class, VaServiceConfigMap.class, TokenServiceConfigMap.class})
public class OrchestratorServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrchestratorServiceApplication.class, args);
    }
}