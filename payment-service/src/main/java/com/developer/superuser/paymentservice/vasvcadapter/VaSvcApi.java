package com.developer.superuser.paymentservice.vasvcadapter;

import com.developer.superuser.paymentservice.core.helper.ApiHelper;
import com.developer.superuser.paymentservice.core.property.VaSvcConfigProperties;
import com.developer.superuser.shared.openapi.contract.VaRequest;
import com.developer.superuser.shared.openapi.contract.VaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class VaSvcApi {
    private final RestClient vaSvcRestClient;
    private final VaSvcConfigProperties vaSvcConfig;
    private final ApiHelper<VaResponse> apiHelper;

    public VaResponse createVa(VaRequest request) {
        return apiHelper.execute(() ->
                vaSvcRestClient.post()
                        .uri(vaSvcConfig.getEndpoint().get("va-create"))
                        .body(request)
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {
                        }));
    }
}