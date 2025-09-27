package com.developer.superuser.orchestratorservice.paymentflowadapter;

import com.developer.superuser.orchestratorservice.paymentflow.PaymentFlowConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@Slf4j
public class PaymentFlowConfigAdapter implements PaymentFlowConfig {
    @Bean
    @Override
    public MessageChannel paymentChannel() {
        log.info("Initializing payment channel...");
        return MessageChannels.direct().getObject();
    }

    @Bean
    @Override
    public IntegrationFlow createPaymentFlow() {
        log.info("Initializing create payment flow...");
        return null;
    }
}