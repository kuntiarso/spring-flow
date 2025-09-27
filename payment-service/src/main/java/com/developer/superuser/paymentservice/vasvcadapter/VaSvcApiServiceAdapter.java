package com.developer.superuser.paymentservice.vasvcadapter;

import com.developer.superuser.paymentservice.paymentresource.mapper.TokenSvcMapper;
import com.developer.superuser.paymentservice.tokensvc.TokenSvcApiService;
import com.developer.superuser.paymentservice.vasvc.VaSvcApiService;
import com.developer.superuser.shared.openapi.contract.TokenResponse;
import com.developer.superuser.shared.openapi.contract.VaRequest;
import com.developer.superuser.shared.openapi.contract.VaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class VaSvcApiServiceAdapter implements VaSvcApiService {
    private final TokenSvcMapper tokenSvcMapper;
    private final TokenSvcApiService tokenSvcApiService;
    private final VaSvcApi vaSvcApi;

    @Override
    public VaResponse createVa(VaRequest request) {
        log.debug("Calling va svc api for creating va");
        TokenResponse token = tokenSvcApiService.getToken(tokenSvcMapper.mapRequest());
        log.info("Printing token b2b result --- {}", token);
        request.setToken(token.getAccessToken());
        return vaSvcApi.createVa(request);
    }
}