package com.developer.superuser.tokenservice.tokenadapter.cache;

import com.developer.superuser.tokenservice.TokenServiceConstant;
import com.developer.superuser.tokenservice.token.Token;
import com.developer.superuser.tokenservice.token.TokenApiService;
import com.developer.superuser.tokenservice.token.TokenCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@RequiredArgsConstructor
@Slf4j
public class TokenCacheServiceAdapter implements TokenCacheService {
    private final TokenApiService tokenApiService;

    @Override
    @Cacheable(
            value = TokenServiceConstant.CACHE_TOKEN_B2B,
            key = "#token.clientId",
            unless = "#result.error != null"
    )
    public Token getOrFetchTokenB2b(Token token) {
        log.debug("Cache is not available for token b2b, then fetching from doku api");
        return tokenApiService.fetchTokenB2b(token);
    }

    @Override
    @CacheEvict(value = TokenServiceConstant.CACHE_TOKEN_B2B, key = "#clientId")
    public void evictTokenB2b(String clientId) {
        log.info("Evicting token b2b from cache for clientId --- {}", clientId);
    }
}