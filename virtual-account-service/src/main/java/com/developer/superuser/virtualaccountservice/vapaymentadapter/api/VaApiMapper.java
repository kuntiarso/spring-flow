package com.developer.superuser.virtualaccountservice.vapaymentadapter.api;

import com.developer.superuser.shared.project.springodt.helper.Digest;
import com.developer.superuser.shared.project.springodt.sign.Sign;
import com.developer.superuser.virtualaccountservice.core.property.DokuConfigProperties;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class VaApiMapper {
    private final DokuConfigProperties dokuConfig;
    private final Digest digest;
    private final ObjectMapper mapper;

    @SneakyThrows
    public Sign mapSign(VaDetail va) {
        return Sign.builder()
                .setHttpMethod(HttpMethod.POST.name())
                .setEndpoint(dokuConfig.getApi().getEndpoint().get("va-create"))
                .setToken(va.getToken())
                .setDigest(digest.generate(mapper.writeValueAsString(va)))
                .setTimestamp(Instant.now())
                .build();
    }
}