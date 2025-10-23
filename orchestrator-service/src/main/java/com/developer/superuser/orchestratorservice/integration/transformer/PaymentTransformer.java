package com.developer.superuser.orchestratorservice.integration.transformer;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.resource.PaymentIntegrationDto;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.PaymentCreateRequest;
import com.developer.superuser.shared.dto.springflow.PaymentCreateResponse;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateRequest;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class PaymentTransformer {
    @Transformer
    public PaymentCreateRequest mapPaymentCreateRequest(@Payload PaymentIntegrationDto dto) {
        log.info("mapPaymentCreateRequest --- {}", dto);
        return dto.getPaymentCreateRequest();
    }

    public PaymentCreateRequest mapPaymentCreateRequest(Message<?> message) {
        log.info("In mapPaymentCreateRequest --- {}", message.getPayload());
        return ((PaymentIntegrationDto) message.getPayload()).getPaymentCreateRequest();
    }

    @Transformer
    public PaymentIntegrationDto addPaymentCreateResponse(@Header(OrchestratorServiceConstant.HEADER_X_TEMP) PaymentIntegrationDto dto,
                                                          @Payload ResponseData<PaymentCreateResponse> response) {
        log.info("addPaymentCreateResponse --- {}", response);
        dto.setPaymentCreateResponse(response.getBody());
        return dto;
    }

    public PaymentIntegrationDto addPaymentCreateResponse(Message<?> message) {
        log.info("In addPaymentCreateResponse --- {}", message.getPayload());
        PaymentIntegrationDto dto = (PaymentIntegrationDto) message.getHeaders().get(OrchestratorServiceConstant.HEADER_X_TEMP);
        if (Objects.isNull(dto)) dto = new PaymentIntegrationDto();
        if (message.getPayload() instanceof PaymentCreateResponse) {
            dto.setPaymentCreateResponse((PaymentCreateResponse) message.getPayload());
        }
        return dto;
    }

    @Transformer
    public PaymentUpdateRequest mapPaymentUpdateRequest(@Payload PaymentIntegrationDto dto) {
        log.info("mapPaymentUpdateRequest --- {}", dto);
        if (Objects.isNull(dto.getPaymentCreateRequest())) {
            log.warn("dto.getPaymentCreateRequest() is null");
            return dto.getPaymentUpdateRequest();
        }
        PaymentUpdateRequest request = PaymentUpdateRequest.builder()
                .withPaymentId(dto.getPaymentCreateResponse().paymentId())
                .withStatus("AWAITING_PAYMENT")
                .withAmount(dto.getVaCreateResponse().billedAmount())
                .build();
        dto.setPaymentUpdateRequest(request);
        return request;
    }

    @Transformer
    public PaymentIntegrationDto addPaymentUpdateResponse(@Header(OrchestratorServiceConstant.HEADER_X_TEMP) PaymentIntegrationDto dto,
                                                          @Payload ResponseData<PaymentUpdateResponse> response) {
        log.info("addPaymentUpdateResponse --- {}", response);
        dto.setPaymentUpdateResponse(response.getBody());
        return dto;
    }

    @Transformer
    public ResponseData<?> mapResponseCreatePaymentVa(@Payload PaymentIntegrationDto dto) {
        log.info("Final response for create payment va --- {}", dto);
        return ResponseData.success(PaymentUpdateResponse.builder()
                .withPaymentId(dto.getPaymentUpdateResponse().paymentId())
                .withStatus(dto.getPaymentUpdateResponse().status())
                .withVaNo(dto.getVaCreateResponse().vaNo())
                .withBilledAmount(dto.getVaCreateResponse().billedAmount())
                .withAdditional(dto.getVaCreateResponse().additional())
                .withExpiredAt(dto.getVaCreateResponse().expiredAt())
                .build());
    }
}