package com.developer.superuser.tokenservice.token;

public interface TokenCacheService {
    Token getOrFetchTokenB2b(Token token);
    void evictTokenB2b(String clientId);
}