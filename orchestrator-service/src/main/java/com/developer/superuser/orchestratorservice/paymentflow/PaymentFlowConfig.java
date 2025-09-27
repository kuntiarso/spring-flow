package com.developer.superuser.orchestratorservice.paymentflow;

import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

public interface PaymentFlowConfig {
    MessageChannel paymentChannel();
    IntegrationFlow createPaymentFlow();
}