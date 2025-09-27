package com.developer.superuser.paymentservice.tokensvcadapter;

import com.developer.superuser.paymentservice.tokensvc.TokenSvcApiService;
import com.developer.superuser.shared.openapi.contract.TokenRequest;
import com.developer.superuser.shared.openapi.contract.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class TokenSvcApiServiceAdapter implements TokenSvcApiService {
    private final TokenSvcApi tokenSvcApi;

    @Override
    public TokenResponse getToken(TokenRequest request) {
        log.debug("Calling token svc api for getting token");
        return tokenSvcApi.getToken(request);
    }
}