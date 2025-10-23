package com.developer.superuser.tokenservice.tokenadapter.api;

import com.developer.superuser.shared.openapi.contract.TokenType;
import com.developer.superuser.tokenservice.core.helper.TokenApiHelper;
import com.developer.superuser.tokenservice.core.property.DokuConfigProperties;
import com.developer.superuser.tokenservice.core.utility.Headers;
import com.developer.superuser.tokenservice.token.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenApi {
    private final RestClient dokuRestClient;
    private final DokuConfigProperties dokuConfig;
    private final TokenApiHelper tokenApiHelper;

    public Token fetchTokenB2b(Token token) {
        Map<String, Object> body = Map.of("grantType", token.getGrantType());
        return tokenApiHelper.execute(() ->
                dokuRestClient.post()
                        .uri(dokuConfig.api().endpoint().get("access-token"), TokenType.B2B.getValue())
                        .headers(header -> header.addAll(Headers.multiValueMapHeader(token)))
                        .body(body)
                        .retrieve()
                        .body(Token.class)
        );
    }
}