package com.developer.superuser.paymentservice.tokensvcadapter;

import com.developer.superuser.paymentservice.core.helper.ApiHelper;
import com.developer.superuser.paymentservice.core.property.TokenSvcConfigProperties;
import com.developer.superuser.shared.openapi.contract.TokenRequest;
import com.developer.superuser.shared.openapi.contract.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenSvcApi {
    private final RestClient tokenSvcRestClient;
    private final TokenSvcConfigProperties tokenSvcConfig;
    private final ApiHelper<TokenResponse> apiHelper;

    public TokenResponse getToken(TokenRequest request) {
        return apiHelper.execute(() ->
                tokenSvcRestClient.post()
                        .uri(tokenSvcConfig.getEndpoint().get("get-token"))
                        .body(request)
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {
                        })
        );
    }
}