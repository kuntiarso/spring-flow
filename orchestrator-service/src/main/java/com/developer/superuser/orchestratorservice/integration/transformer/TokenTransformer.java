package com.developer.superuser.orchestratorservice.integration.transformer;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.resource.PaymentIntegrationDto;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.TokenGetRequest;
import com.developer.superuser.shared.dto.springflow.TokenGetResponse;
import com.developer.superuser.shared.openapi.contract.GrantType;
import com.developer.superuser.shared.openapi.contract.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenTransformer {
    @Transformer
    public TokenGetRequest mapTokenGetRequest(@Header(OrchestratorServiceConstant.HEADER_X_TEMP) PaymentIntegrationDto dto) {
        log.info("mapTokenGetRequest --- {}", dto);
        TokenGetRequest request = TokenGetRequest.builder()
                .withTokenType(TokenType.B2B)
                .withGrantType(GrantType.CLIENT_CREDENTIALS)
                .build();
        dto.setTokenGetRequest(request);
        return request;
    }

    @Transformer
    public PaymentIntegrationDto addTokenGetResponse(@Header(OrchestratorServiceConstant.HEADER_X_TEMP) PaymentIntegrationDto dto,
                                                     @Payload ResponseData<TokenGetResponse> response) {
        log.info("addTokenGetResponse --- {}", response);
        dto.setTokenGetResponse(response.getBody());
        return dto;
    }
}