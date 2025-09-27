package com.developer.superuser.virtualaccountservice.core.config;

import com.developer.superuser.shared.project.springodt.helper.Digest;
import com.developer.superuser.shared.project.springodt.sign.Symmetric;
import com.developer.superuser.virtualaccountservice.VirtualAccountServiceConstant;
import com.developer.superuser.virtualaccountservice.core.property.DokuConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({DokuConfigProperties.class})
public class GlobalConfig {
    @Bean
    public Symmetric symmetricSign(DokuConfigProperties dokuConfig) {
        return new Symmetric(VirtualAccountServiceConstant.ALGORITHM_HMAC_SHA512, dokuConfig.getApi().getKey());
    }

    @Bean
    public Digest digest() {
        return new Digest(VirtualAccountServiceConstant.ALGORITHM_DIGEST_SHA256);
    }
}