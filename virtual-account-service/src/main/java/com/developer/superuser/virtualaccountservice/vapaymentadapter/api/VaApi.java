package com.developer.superuser.virtualaccountservice.vapaymentadapter.api;

import com.developer.superuser.virtualaccountservice.core.helper.VaApiHelper;
import com.developer.superuser.virtualaccountservice.core.property.DokuConfigProperties;
import com.developer.superuser.virtualaccountservice.core.utility.Headers;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class VaApi {
    private final RestClient dokuRestClient;
    private final DokuConfigProperties dokuConfig;
    private final VaApiHelper vaApiHelper;

    @SneakyThrows
    public VaDetail dokuCreateVa(VaDetail va) {
        MultiValueMap<String, String> headerMap = Headers.multiValueMapHeader(va);
        return vaApiHelper.execute(() ->
                dokuRestClient.post()
                        .uri(dokuConfig.getApi().getEndpoint().get("va-create"))
                        .headers(header -> header.addAll(headerMap))
                        .body(va)
                        .retrieve()
                        .body(VaDetail.class)
        );
    }
}