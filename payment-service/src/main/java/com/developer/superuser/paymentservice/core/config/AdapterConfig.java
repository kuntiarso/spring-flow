package com.developer.superuser.paymentservice.core.config;

import com.developer.superuser.paymentservice.core.helper.SequenceNumber;
import com.developer.superuser.paymentservice.messaging.MqListenerService;
import com.developer.superuser.paymentservice.messaging.MqPublisherService;
import com.developer.superuser.paymentservice.messagingadapter.MqListenerServiceAdapter;
import com.developer.superuser.paymentservice.messagingadapter.MqPublisherServiceAdapter;
import com.developer.superuser.paymentservice.payment.PaymentPersistenceService;
import com.developer.superuser.paymentservice.paymentadapter.PaymentEntityMapper;
import com.developer.superuser.paymentservice.paymentadapter.PaymentPersistenceServiceAdapter;
import com.developer.superuser.paymentservice.paymentadapter.PaymentRepository;
import com.developer.superuser.paymentservice.paymentresource.mapper.PaymentMapper;
import com.developer.superuser.paymentservice.paymentresource.mapper.TokenSvcMapper;
import com.developer.superuser.paymentservice.status.StatusApiService;
import com.developer.superuser.paymentservice.statusadapter.StatusApi;
import com.developer.superuser.paymentservice.statusadapter.StatusApiMapper;
import com.developer.superuser.paymentservice.statusadapter.StatusApiServiceAdapter;
import com.developer.superuser.paymentservice.statusresource.StatusMapper;
import com.developer.superuser.paymentservice.tokensvc.TokenSvcApiService;
import com.developer.superuser.paymentservice.tokensvcadapter.TokenSvcApi;
import com.developer.superuser.paymentservice.tokensvcadapter.TokenSvcApiServiceAdapter;
import com.developer.superuser.paymentservice.vasvc.VaSvcApiService;
import com.developer.superuser.paymentservice.vasvcadapter.VaSvcApi;
import com.developer.superuser.paymentservice.vasvcadapter.VaSvcApiServiceAdapter;
import com.developer.superuser.shared.project.springodt.sign.Symmetric;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {
    @Bean
    public TokenSvcApiService tokenSvcApiService(TokenSvcApi tokenSvcApi) {
        return new TokenSvcApiServiceAdapter(tokenSvcApi);
    }

    @Bean
    public VaSvcApiService vaSvcApiService(TokenSvcMapper tokenSvcMapper, TokenSvcApiService tokenSvcApiService, VaSvcApi vaSvcApi) {
        return new VaSvcApiServiceAdapter(tokenSvcMapper, tokenSvcApiService, vaSvcApi);
    }

    @Bean
    public PaymentPersistenceService paymentPersistenceService(PaymentRepository paymentRepository, PaymentEntityMapper paymentEntityMapper) {
        return new PaymentPersistenceServiceAdapter(paymentRepository, paymentEntityMapper);
    }

    @Bean
    public StatusApiService statusApiService(TokenSvcMapper tokenSvcMapper, TokenSvcApiService tokenSvcApiService, Symmetric symmetric, StatusApiMapper statusApiMapper, StatusApi statusApi, ObjectMapper mapper) {
        return new StatusApiServiceAdapter(tokenSvcMapper, tokenSvcApiService, symmetric, statusApiMapper, statusApi, mapper);
    }

    @Bean
    public MqPublisherService mqPublisherService(RabbitTemplate rabbitTemplate) {
        return new MqPublisherServiceAdapter(rabbitTemplate);
    }

    @Bean
    public MqListenerService mqListenerService(RabbitTemplate rabbitTemplate, SequenceNumber sequenceNumber, StatusMapper statusMapper, StatusApiService statusApiService, PaymentMapper paymentMapper, PaymentPersistenceService paymentPersistenceService, MqPublisherService mqPublisherService) {
        return new MqListenerServiceAdapter(rabbitTemplate, sequenceNumber, statusMapper, statusApiService, paymentMapper, paymentPersistenceService, mqPublisherService);
    }
}