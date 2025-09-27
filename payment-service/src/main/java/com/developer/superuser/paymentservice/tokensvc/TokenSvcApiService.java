package com.developer.superuser.paymentservice.tokensvc;

import com.developer.superuser.shared.openapi.contract.TokenRequest;
import com.developer.superuser.shared.openapi.contract.TokenResponse;

public interface TokenSvcApiService {
    TokenResponse getToken(TokenRequest request);
}