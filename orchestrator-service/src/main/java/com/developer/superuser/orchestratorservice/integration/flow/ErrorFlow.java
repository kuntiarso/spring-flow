package com.developer.superuser.orchestratorservice.integration.flow;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.integration.gateway.PaymentGateway;
import com.developer.superuser.orchestratorservice.resource.PaymentIntegrationDto;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateRequest;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.utility.Errors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ErrorFlow {
    private final ObjectMapper mapper;
    private final PaymentGateway paymentGateway;

    @Bean
    public IntegrationFlow errorCreatePaymentVa() {
        return flow -> flow
                .handle(ErrorMessage.class, (errorMessage, headers) -> {
                    MessagingException ex = (MessagingException) errorMessage.getPayload();
                    Throwable cause = ex.getCause();
                    Message<?> message = ex.getFailedMessage();
                    ErrorData errorData;
                    if (cause instanceof HttpStatusCodeException httpEx) {
                        try {
                            log.error("Error caught by HttpStatusCodeException ::: {}", httpEx.getLocalizedMessage());
                            ResponseData<ErrorData> response = mapper.readValue(httpEx.getResponseBodyAsString(), new TypeReference<>() {
                            });
                            errorData = response.getBody();
                        } catch (JsonProcessingException jsEx) {
                            log.error("Error caught by JsonProcessingException ::: {}", jsEx.getLocalizedMessage());
                            errorData = Errors.internalServerError(jsEx.getLocalizedMessage());
                        }
                    } else {
                        log.error("Error caught by unknown exception");
                        errorData = Errors.internalServerError(cause.getLocalizedMessage());
                    }
                    log.error("Error data in error channel ::: {}", errorData);
                    String paymentId = null;
                    if (Objects.nonNull(message)) {
                        headers = message.getHeaders();
                        PaymentIntegrationDto dto = (PaymentIntegrationDto) headers.get(OrchestratorServiceConstant.HEADER_X_TEMP);
                        if (Objects.nonNull(dto) && Objects.nonNull(dto.getPaymentCreateResponse())) {
                            paymentId = dto.getPaymentCreateResponse().paymentId();
                            log.error("Found paymentId in error channel ::: {}", paymentId);
                        }
                    }
                    if (Objects.nonNull(paymentId)) {
                        log.error("Update payment status as ::: failed");
                        PaymentUpdateRequest request = PaymentUpdateRequest.builder()
                                .withPaymentId(paymentId)
                                .withStatus("FAILED")
                                .withError(errorData)
                                .build();
                        paymentGateway.updatePayment(PaymentIntegrationDto.builder()
                                .withPaymentUpdateRequest(request)
                                .build());
                    }
                    return errorData;
                });
    }

    @Bean
    public IntegrationFlow errorMapResponseData() {
        return flow -> flow
                .<ErrorData, ResponseData<ErrorData>>transform(ResponseData::success)
                .log(m -> "Error response data ::: " + m.getPayload());
    }
}