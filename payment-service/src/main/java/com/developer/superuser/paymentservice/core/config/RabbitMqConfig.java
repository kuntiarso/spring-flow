package com.developer.superuser.paymentservice.core.config;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private static final String TTL = "x-message-ttl";
    private static final String DLX = "x-dead-letter-exchange";
    private static final String DLRK = "x-dead-letter-routing-key";

    @Bean
    public MessageConverter messageConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    /**
     * @return DirectExchange
     */
    @Bean
    public DirectExchange paymentStatusExchange() {
        return new DirectExchange(PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS);
    }

    /**
     * @return Queue
     */
    @Bean
    public Queue paymentStatusQueue() {
        return QueueBuilder.durable(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS)
                .withArgument(DLX, PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS)
                .withArgument(DLRK, PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_60S)
                .build();
    }

    @Bean
    public Queue paymentStatusQueue60s() {
        return QueueBuilder.durable(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_60S)
                .withArgument(TTL, 60_000)
                .withArgument(DLX, PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS)
                .withArgument(DLRK, PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS)
                .build();
    }

    @Bean
    public Queue paymentStatusQueue120s() {
        return QueueBuilder.durable(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_120S)
                .withArgument(TTL, 120_000)
                .withArgument(DLX, PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS)
                .withArgument(DLRK, PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS)
                .build();
    }

    @Bean
    public Queue paymentStatusQueue180s() {
        return QueueBuilder.durable(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_180S)
                .withArgument(TTL, 180_000)
                .withArgument(DLX, PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS)
                .withArgument(DLRK, PaymentServiceConstant.MQ_DLQ_PAYMENT_STATUS)
                .build();
    }

    @Bean
    public Queue paymentStatusDlq() {
        return QueueBuilder.durable(PaymentServiceConstant.MQ_DLQ_PAYMENT_STATUS).build();
    }

    /**
     * @return Binding
     */
    @Bean
    public Binding paymentStatusBinding(Queue paymentStatusQueue, DirectExchange paymentStatusExchange) {
        return BindingBuilder.bind(paymentStatusQueue)
                .to(paymentStatusExchange)
                .with(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS);
    }

    @Bean
    public Binding paymentStatusBinding60s(Queue paymentStatusQueue60s, DirectExchange paymentStatusExchange) {
        return BindingBuilder.bind(paymentStatusQueue60s)
                .to(paymentStatusExchange)
                .with(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_60S);
    }

    @Bean
    public Binding paymentStatusBinding120s(Queue paymentStatusQueue120s, DirectExchange paymentStatusExchange) {
        return BindingBuilder.bind(paymentStatusQueue120s)
                .to(paymentStatusExchange)
                .with(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_120S);
    }

    @Bean
    public Binding paymentStatusBinding180s(Queue paymentStatusQueue180s, DirectExchange paymentStatusExchange) {
        return BindingBuilder.bind(paymentStatusQueue180s)
                .to(paymentStatusExchange)
                .with(PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_180S);
    }

    @Bean
    public Binding paymentStatusBindingDlq(Queue paymentStatusDlq, DirectExchange paymentStatusExchange) {
        return BindingBuilder.bind(paymentStatusDlq)
                .to(paymentStatusExchange)
                .with(PaymentServiceConstant.MQ_DLQ_PAYMENT_STATUS);
    }
}