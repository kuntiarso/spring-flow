package com.developer.superuser.tokenservice.core.config;

import com.developer.superuser.shared.project.springodt.sign.Basic;
import com.developer.superuser.tokenservice.TokenServiceConstant;
import com.developer.superuser.tokenservice.core.property.DokuConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;

@Configuration
@EnableConfigurationProperties(DokuConfigProperties.class)
public class GlobalConfig {
    @Bean
    public Basic basicSign(PrivateKey privateKey) {
        return new Basic(TokenServiceConstant.ALGORITHM_RSA_SHA256, privateKey);
    }
}