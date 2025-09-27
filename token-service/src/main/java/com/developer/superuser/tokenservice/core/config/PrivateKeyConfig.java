package com.developer.superuser.tokenservice.core.config;

import com.developer.superuser.tokenservice.TokenServiceConstant;
import com.developer.superuser.tokenservice.core.property.DokuConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Configuration
@RequiredArgsConstructor
public class PrivateKeyConfig {
    private final ResourceLoader resourceLoader;

    @Bean
    public PrivateKey merchantPrivateKey(DokuConfigProperties dokuConfig) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance(TokenServiceConstant.KEY_FORMAT_PKCS12);
        DokuConfigProperties.Merchant merchant = dokuConfig.getMerchant();
        Resource resource = resourceLoader.getResource(merchant.getKeyStore().getLocation());
        try (InputStream inputStream = resource.getInputStream()) {
            keyStore.load(inputStream, merchant.getKeyStore().getPassword().toCharArray());
        }
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(merchant.getPrivateKey().getPassphrase().toCharArray());
        KeyStore.Entry entry = keyStore.getEntry(merchant.getKeyStore().getAlias(), passwordProtection);
        if (entry instanceof KeyStore.PrivateKeyEntry) {
            return ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
        } else {
            throw new KeyStoreException("Failing to retrieve private key from keystore, probably due to invalid keystore alias or password");
        }
    }
}