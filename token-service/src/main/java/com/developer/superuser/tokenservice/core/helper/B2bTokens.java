package com.developer.superuser.tokenservice.core.helper;

import com.developer.superuser.shared.helper.Executor;
import com.developer.superuser.shared.helper.Validator;
import com.developer.superuser.shared.openapi.contract.TokenRequest;
import com.developer.superuser.tokenservice.token.Token;
import com.developer.superuser.tokenservice.token.TokenCacheService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class B2bTokens implements Validator<TokenRequest, Void>, Executor<Token, Token> {
    private final TokenCacheService tokenCacheService;

    @Override
    public Void validate(TokenRequest request) {
        Preconditions.checkNotNull(request, "request must not be null");
        Preconditions.checkNotNull(request.getTokenType(), "tokenType must not be null");
        Preconditions.checkNotNull(request.getGrantType(), "grantType must not be null");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(request.getClientId()), "clientId must not be null or empty");
        return null;
    }

    @Override
    public Token execute(Token token) {
        Token tokenResult = tokenCacheService.getOrFetchTokenB2b(token);
        log.info("Printing token b2b result --- {}", tokenResult);
        return tokenResult;
    }
}