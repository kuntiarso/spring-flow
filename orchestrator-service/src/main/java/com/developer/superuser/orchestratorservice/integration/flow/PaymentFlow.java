package com.developer.superuser.orchestratorservice.integration.flow;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.core.helper.SequenceNumber;
import com.developer.superuser.orchestratorservice.core.property.PaymentServiceConfigMap;
import com.developer.superuser.orchestratorservice.integration.filter.ResponseFilter;
import com.developer.superuser.orchestratorservice.integration.transformer.PaymentTransformer;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.PaymentCreateResponse;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PaymentFlow {
    @Bean
    public IntegrationFlow flowCreatePaymentVa(PaymentTransformer paymentTransformer, ResponseFilter responseFilter) {
        return IntegrationFlow.from("channelCreatePaymentVa")
                .gateway("httpCreatePayment.input", s -> s.errorChannel("errorCreatePaymentVa.input"))
                .filter(responseFilter, "isSuccess", s -> s.discardChannel("errorMapResponseData.input").throwExceptionOnRejection(false))
                .gateway("httpGetToken.input", s -> s.errorChannel("errorCreatePaymentVa.input"))
                .filter(responseFilter, "isSuccess", s -> s.discardChannel("errorMapResponseData.input").throwExceptionOnRejection(false))
                .gateway("httpCreateVa.input", s -> s.errorChannel("errorCreatePaymentVa.input"))
                .filter(responseFilter, "isSuccess", s -> s.discardChannel("errorMapResponseData.input").throwExceptionOnRejection(false))
                .gateway("httpUpdatePayment.input", s -> s.errorChannel("errorCreatePaymentVa.input"))
                .filter(responseFilter, "isSuccess", s -> s.discardChannel("errorMapResponseData.input").throwExceptionOnRejection(false))
                .transform(paymentTransformer, "mapResponseCreatePaymentVa")
                .get();
    }

    @Bean
    public IntegrationFlow httpCreatePayment(PaymentServiceConfigMap configMap,
                                             SequenceNumber sequenceNumber,
                                             PaymentTransformer paymentTransformer,
                                             DefaultHttpHeaderMapper withDefaultHeaders) {
        log.debug("REST api call for create payment");
        URI uri = URI.create(configMap.baseUrl() + configMap.endpoint().get("payment-create"));
        log.info("URI for create payment --- {}", uri);
        return flow -> flow
                .enrichHeaders(h -> {
                    h.headerFunction(OrchestratorServiceConstant.HEADER_X_REQUEST_ID, m -> sequenceNumber.generate());
                    h.headerExpression(OrchestratorServiceConstant.HEADER_X_TEMP, "payload");
                })
                .transform(paymentTransformer, "mapPaymentCreateRequest")
                .handle(Http.outboundGateway(uri)
                        .httpMethod(HttpMethod.POST)
                        .headerMapper(withDefaultHeaders)
                        .expectedResponseType(new ParameterizedTypeReference<ResponseData<PaymentCreateResponse>>() {
                        })
                        .extractPayload(true))
                .transform(paymentTransformer, "addPaymentCreateResponse")
                .log(m -> "Response from httpCreatePayment --- " + m.getPayload());
    }

    @Bean
    public IntegrationFlow httpUpdatePayment(PaymentServiceConfigMap configMap, PaymentTransformer paymentTransformer, DefaultHttpHeaderMapper withDefaultHeaders) {
        log.debug("REST api call for update payment");
        URI uri = URI.create(configMap.baseUrl() + configMap.endpoint().get("payment-update"));
        log.info("URI for update payment --- {}", uri);
        return flow -> flow
                .enrichHeaders(h -> h.headerExpression(OrchestratorServiceConstant.HEADER_X_TEMP, "payload"))
                .transform(paymentTransformer, "mapPaymentUpdateRequest")
                .handle(Http.outboundGateway(uri)
                        .httpMethod(HttpMethod.PUT)
                        .headerMapper(withDefaultHeaders)
                        .expectedResponseType(new ParameterizedTypeReference<ResponseData<PaymentUpdateResponse>>() {
                        })
                        .extractPayload(true))
                .transform(paymentTransformer, "addPaymentUpdateResponse")
                .log(m -> "Response from httpUpdatePayment --- " + m.getPayload());
    }
}