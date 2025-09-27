package com.developer.superuser.paymentservice.statusadapter;

import com.developer.superuser.paymentservice.core.property.DokuConfigProperties;
import com.developer.superuser.shared.openapi.contract.DokuStatusRequest;
import com.developer.superuser.shared.openapi.contract.DokuStatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatusApi {
    private final RestClient dokuRestClient;
    private final DokuConfigProperties dokuConfig;

    public DokuStatusResponse checkPaymentStatus(MultiValueMap<String, String> header, DokuStatusRequest request) {
        return dokuRestClient.post()
                .uri(dokuConfig.getApi().getEndpoint().get("va-status"))
                .headers(httpHeaders -> httpHeaders.putAll(header))
                .body(request)
                .retrieve()
                .body(DokuStatusResponse.class);
    }
}