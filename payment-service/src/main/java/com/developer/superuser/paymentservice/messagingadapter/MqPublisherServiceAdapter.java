package com.developer.superuser.paymentservice.messagingadapter;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.developer.superuser.paymentservice.messaging.MqPublisherService;
import com.developer.superuser.shared.openapi.contract.StatusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
@Slf4j
public class MqPublisherServiceAdapter implements MqPublisherService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendPaymentStatusQueueSuccess(StatusResponse response) {
        log.info("Sending payment.status.queue.success for va");
        rabbitTemplate.convertAndSend(PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS, PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_SUCCESS, response);
    }

    @Override
    public void sendPaymentStatusQueueError(StatusResponse response) {
        log.info("Sending payment.status.queue.error for va");
        rabbitTemplate.convertAndSend(PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS, PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_ERROR, response);

    }
}