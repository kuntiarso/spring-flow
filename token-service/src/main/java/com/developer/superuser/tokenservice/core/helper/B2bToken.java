package com.developer.superuser.tokenservice.core.helper;

import com.developer.superuser.shared.dto.springflow.TokenGetRequest;
import com.developer.superuser.shared.helper.Executor;
import com.developer.superuser.shared.helper.Validator;
import com.developer.superuser.tokenservice.token.Token;
import com.developer.superuser.tokenservice.token.TokenCacheService;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class B2bToken implements Validator<TokenGetRequest, Void>, Executor<Token, Token> {
    private final TokenCacheService tokenCacheService;

    @Override
    public Void validate(TokenGetRequest request) {
        Preconditions.checkNotNull(request, "request object must not be null");
        Preconditions.checkNotNull(request.tokenType(), "tokenType must not be null");
        Preconditions.checkNotNull(request.grantType(), "grantType must not be null");
        return null;
    }

    @Override
    public Token execute(Token token) {
        return tokenCacheService.getOrFetchTokenB2b(token);
    }
}