package com.developer.superuser.orchestratorservice.integration.handler;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.core.helper.SequenceNumber;
import com.developer.superuser.orchestratorservice.core.property.PaymentServiceConfigMap;
import com.developer.superuser.orchestratorservice.integration.transformer.PaymentTransformer;
import com.developer.superuser.orchestratorservice.resource.PaymentIntegrationDto;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.PaymentCreateRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentHandler implements MessageHandler {
    private final PaymentServiceConfigMap configMap;
    private final DefaultHttpHeaderMapper withDefaultHeaders;
    private final SequenceNumber sequenceNumber;
    private final PaymentTransformer paymentTransformer;

    private MessageHandler handler;

    @PostConstruct
    public void init() {
        log.debug("REST api call for create payment");
        URI uri = URI.create(configMap.baseUrl() + configMap.endpoint().get("payment-create"));
        log.info("URI for create payment --- {}", uri);
        this.handler = Http.outboundGateway(uri)
                .httpMethod(HttpMethod.POST)
                .headerMapper(withDefaultHeaders)
                .expectedResponseType(new ParameterizedTypeReference<ResponseData<?>>() {
                })
                .extractPayload(true)
                .getObject();
    }

    @Override
    public void handleMessage(Message<?> inboundMessage) throws MessagingException {
        log.info("Received request for create payment --- {}", inboundMessage.getPayload());
        Message<?> enriched = MessageBuilder.fromMessage(inboundMessage)
                .setHeader(OrchestratorServiceConstant.HEADER_X_REQUEST_ID, sequenceNumber.generate())
                .setHeader(OrchestratorServiceConstant.HEADER_X_TEMP, inboundMessage.getPayload())
                .build();
        Message<PaymentCreateRequest> outboundMessage = MessageBuilder
                .withPayload(paymentTransformer.mapPaymentCreateRequest(enriched))
                .copyHeaders(enriched.getHeaders())
                .build();
        try {
            handler.handleMessage(outboundMessage);
        } catch (Exception ex) {
            log.error("Error during httpCreatePayment call ::: {}", ex.getLocalizedMessage());
            throw new MessagingException(outboundMessage, "Error during httpCreatePayment call", ex);
        }
        PaymentIntegrationDto dto = paymentTransformer.addPaymentCreateResponse(outboundMessage);
        log.info("Success response from httpCreatePayment --- {}", dto);
    }
}