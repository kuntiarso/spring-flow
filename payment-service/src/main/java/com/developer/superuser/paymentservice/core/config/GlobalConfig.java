package com.developer.superuser.paymentservice.core.config;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.developer.superuser.paymentservice.core.property.DokuConfigProperties;
import com.developer.superuser.paymentservice.core.property.TokenSvcConfigProperties;
import com.developer.superuser.paymentservice.core.property.VaSvcConfigProperties;
import com.developer.superuser.shared.project.springodt.helper.Digest;
import com.developer.superuser.shared.project.springodt.sign.Symmetric;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({TokenSvcConfigProperties.class, VaSvcConfigProperties.class, DokuConfigProperties.class})
public class GlobalConfig {
    @Bean
    public Symmetric symmetric(DokuConfigProperties dokuConfig) {
        return new Symmetric(PaymentServiceConstant.ALGORITHM_HMAC_SHA512, dokuConfig.getApi().getKey());
    }

    @Bean
    public Digest digest() {
        return new Digest(PaymentServiceConstant.ALGORITHM_SHA256);
    }
}