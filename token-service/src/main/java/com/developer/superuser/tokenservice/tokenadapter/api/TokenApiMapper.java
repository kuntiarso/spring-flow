package com.developer.superuser.tokenservice.tokenadapter.api;

import com.developer.superuser.shared.project.springodt.sign.Sign;
import com.developer.superuser.tokenservice.token.Token;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenApiMapper {
    public Sign mapSign(Token token) {
        return Sign.builder()
                .setClientId(token.getClientId())
                .setTimestamp(Instant.now())
                .build();
    }
}