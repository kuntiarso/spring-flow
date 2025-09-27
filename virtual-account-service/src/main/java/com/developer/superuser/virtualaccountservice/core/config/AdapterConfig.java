package com.developer.superuser.virtualaccountservice.core.config;

import com.developer.superuser.shared.project.springodt.sign.Symmetric;
import com.developer.superuser.virtualaccountservice.messaging.MqListenerService;
import com.developer.superuser.virtualaccountservice.messaging.MqPublisherService;
import com.developer.superuser.virtualaccountservice.messagingadapter.MqListenerServiceAdapter;
import com.developer.superuser.virtualaccountservice.messagingadapter.MqPublisherServiceAdapter;
import com.developer.superuser.virtualaccountservice.vapayment.VaApiService;
import com.developer.superuser.virtualaccountservice.vapayment.VaPersistenceService;
import com.developer.superuser.virtualaccountservice.vapaymentadapter.api.VaApiMapper;
import com.developer.superuser.virtualaccountservice.vapaymentadapter.api.VaApi;
import com.developer.superuser.virtualaccountservice.vapaymentadapter.api.VaApiServiceAdapter;
import com.developer.superuser.virtualaccountservice.vapaymentadapter.db.VaPaymentDetailEntityMapper;
import com.developer.superuser.virtualaccountservice.vapaymentadapter.db.VaPersistenceServiceAdapter;
import com.developer.superuser.virtualaccountservice.vapaymentadapter.db.VaRepository;
import com.developer.superuser.virtualaccountservice.vapaymentresource.VaMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {
    @Bean
    public VaPersistenceService vaPersistenceService(VaRepository vaRepository, VaPaymentDetailEntityMapper vaPaymentDetailEntityMapper) {
        return new VaPersistenceServiceAdapter(vaRepository, vaPaymentDetailEntityMapper);
    }

    @Bean
    public VaApiService vaApiService(Symmetric symmetric, VaApiMapper vaApiMapper, VaApi vaApi) {
        return new VaApiServiceAdapter(symmetric, vaApiMapper, vaApi);
    }

    @Bean
    public MqPublisherService mqPublisherService(RabbitTemplate rabbitTemplate) {
        return new MqPublisherServiceAdapter(rabbitTemplate);
    }

    @Bean
    public MqListenerService mqListenerService(VaMapper vaMapper, VaPersistenceService vaPersistenceService) {
        return new MqListenerServiceAdapter(vaMapper, vaPersistenceService);
    }
}