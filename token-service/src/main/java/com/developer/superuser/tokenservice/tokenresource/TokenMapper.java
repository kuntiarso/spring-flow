package com.developer.superuser.tokenservice.tokenresource;

import com.developer.superuser.shared.openapi.contract.TokenRequest;
import com.developer.superuser.shared.openapi.contract.TokenResponse;
import com.developer.superuser.tokenservice.token.Token;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public Token mapCore(TokenRequest request) {
        return Token.builder()
                .setClientId(request.getClientId())
                .setGrantType(request.getGrantType().getValue())
                .build();
    }

    public TokenResponse mapResponse(Token token) {
        return TokenResponse.builder()
                .setTokenScheme(token.getTokenScheme())
                .setAccessToken(token.getAccessToken())
                .setAccessTokenExpiryTime(token.getAccessTokenExpiryTime())
                .setRefreshToken(token.getRefreshToken())
                .setRefreshTokenExpiryTime(token.getRefreshTokenExpiryTime())
                .setExpiresIn(token.getExpiresIn())
                .setAdditionalInfo(token.getAdditionalInfo())
                .build();
    }
}