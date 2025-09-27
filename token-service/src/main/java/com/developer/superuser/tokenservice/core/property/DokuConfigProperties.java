package com.developer.superuser.tokenservice.core.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "doku")
@Data
public class DokuConfigProperties {
    private Merchant merchant;
    private Api api;

    @Data
    public static class Merchant {
        private KeyStore keyStore;
        private PrivateKey privateKey;
    }

    @Data
    public static class KeyStore {
        private String location;
        private String alias;
        private String password;
    }

    @Data
    public static class PrivateKey {
        private String passphrase;
    }

    @Data
    public static class Api {
        private String key;
        private String baseUrl;
        private Map<String, String> endpoint;
    }
}