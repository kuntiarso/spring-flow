package com.developer.superuser.paymentservice.paymentresource.mapper;

import com.developer.superuser.paymentservice.core.property.DokuConfigProperties;
import com.developer.superuser.shared.openapi.contract.GrantType;
import com.developer.superuser.shared.openapi.contract.TokenRequest;
import com.developer.superuser.shared.openapi.contract.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenSvcMapper {
    private final DokuConfigProperties dokuConfig;

    public TokenRequest mapRequest() {
        return TokenRequest.builder()
                .setClientId(dokuConfig.getMerchant().getClientId())
                .setTokenType(TokenType.B2B)
                .setGrantType(GrantType.CLIENT_CREDENTIALS)
                .build();
    }
}