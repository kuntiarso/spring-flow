package com.developer.superuser.orchestratorservice.integration.transformer;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.resource.PaymentIntegrationDto;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.VaCreateRequest;
import com.developer.superuser.shared.dto.springflow.VaCreateResponse;
import com.developer.superuser.shared.openapi.contract.AdditionalData;
import com.developer.superuser.shared.openapi.contract.TokenScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VaTransformer {
    @Transformer
    public VaCreateRequest mapVaCreateRequest(@Header(OrchestratorServiceConstant.HEADER_X_TEMP) PaymentIntegrationDto dto) {
        log.info("mapVaCreateRequest --- {}", dto);
        VaCreateRequest request = VaCreateRequest.builder()
                .withTokenScheme(TokenScheme.BEARER)
                .withToken(dto.getTokenGetResponse().accessToken())
                .withPaymentId(dto.getPaymentCreateResponse().paymentId())
                .withPartnerId(dto.getPaymentCreateRequest().partnerId())
                .withCustomerNo(dto.getPaymentCreateRequest().customerNo())
                .withVaNo(dto.getPaymentCreateRequest().vaNo())
                .withVaName(dto.getPaymentCreateRequest().vaName())
                .withBilledAmount(dto.getPaymentCreateRequest().amount())
                .withTransactionType(dto.getPaymentCreateRequest().transactionType())
                .withAdditional(AdditionalData.builder()
                        .setChannel(dto.getPaymentCreateRequest().channel())
                        .build())
                .build();
        dto.setVaCreateRequest(request);
        return request;
    }

    @Transformer
    public PaymentIntegrationDto addVaCreateResponse(@Header(OrchestratorServiceConstant.HEADER_X_TEMP) PaymentIntegrationDto dto,
                                                     @Payload ResponseData<VaCreateResponse> response) {
        log.info("addVaCreateResponse --- {}", response);
        dto.setVaCreateResponse(response.getBody());
        return dto;
    }
}