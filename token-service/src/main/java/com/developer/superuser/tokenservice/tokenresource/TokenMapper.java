package com.developer.superuser.tokenservice.tokenresource;

import com.developer.superuser.shared.dto.springflow.TokenGetRequest;
import com.developer.superuser.shared.dto.springflow.TokenGetResponse;
import com.developer.superuser.tokenservice.core.property.DokuConfigProperties;
import com.developer.superuser.tokenservice.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {
    public Token mapCore(String clientId, TokenGetRequest request) {
        return Token.builder()
                .setClientId(clientId)
                .setGrantType(request.grantType().getValue())
                .build();
    }

    public TokenGetResponse mapResponse(Token token) {
        return TokenGetResponse.builder()
                .withTokenScheme(token.getTokenScheme())
                .withAccessToken(token.getAccessToken())
                .withAccessTokenExpiryTime(token.getAccessTokenExpiryTime())
                .withRefreshToken(token.getRefreshToken())
                .withRefreshTokenExpiryTime(token.getRefreshTokenExpiryTime())
                .build();
    }
}