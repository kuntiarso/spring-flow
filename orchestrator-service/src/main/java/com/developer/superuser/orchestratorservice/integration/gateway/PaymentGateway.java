package com.developer.superuser.orchestratorservice.integration.gateway;

import com.developer.superuser.orchestratorservice.resource.PaymentIntegrationDto;
import com.developer.superuser.shared.data.ResponseData;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface PaymentGateway {
    @Gateway(requestChannel = "channelCreatePaymentVa")
    ResponseData<?> createPaymentVa(PaymentIntegrationDto dto);

    @Gateway(requestChannel = "httpUpdatePayment.input")
    void updatePayment(PaymentIntegrationDto dto);
}