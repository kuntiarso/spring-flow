package com.developer.superuser.virtualaccountservice.core.config;

import com.developer.superuser.virtualaccountservice.VirtualAccountServiceConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean
    public MessageConverter messageConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    /**
     * @return DirectExchange
     */
    @Bean
    public DirectExchange paymentStatusExchange() {
        return new DirectExchange(VirtualAccountServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS);
    }

    /**
     * @return Queue
     */
    @Bean
    public Queue paymentStatusQueueSuccess() {
        return QueueBuilder.durable(VirtualAccountServiceConstant.MQ_QUEUE_PAYMENT_STATUS_SUCCESS).build();
    }

    @Bean
    public Queue paymentStatusQueueError() {
        return QueueBuilder.durable(VirtualAccountServiceConstant.MQ_QUEUE_PAYMENT_STATUS_ERROR).build();
    }

    /**
     * @return Binding
     */
    @Bean
    public Binding paymentStatusBindingSuccess(Queue paymentStatusQueueSuccess, DirectExchange paymentStatusExchange) {
        return BindingBuilder.bind(paymentStatusQueueSuccess)
                .to(paymentStatusExchange)
                .with(VirtualAccountServiceConstant.MQ_QUEUE_PAYMENT_STATUS_SUCCESS);
    }

    @Bean
    public Binding paymentStatusBindingError(Queue paymentStatusQueueError, DirectExchange paymentStatusExchange) {
        return BindingBuilder.bind(paymentStatusQueueError)
                .to(paymentStatusExchange)
                .with(VirtualAccountServiceConstant.MQ_QUEUE_PAYMENT_STATUS_ERROR);
    }
}