package com.developer.superuser.virtualaccountservice.messagingadapter;

import com.developer.superuser.shared.openapi.contract.VaResponse;
import com.developer.superuser.virtualaccountservice.VirtualAccountServiceConstant;
import com.developer.superuser.virtualaccountservice.messaging.MqPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RequiredArgsConstructor
@Slf4j
public class MqPublisherServiceAdapter implements MqPublisherService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendPaymentStatusQueue(VaResponse response) {
        log.info("Sending payment.status.queue.60s for payment --- {}", response);
        rabbitTemplate.convertAndSend(
                VirtualAccountServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS,
                VirtualAccountServiceConstant.MQ_QUEUE_PAYMENT_STATUS_60S,
                response,
                this::setFirstRetryCount
        );
    }

    private Message setFirstRetryCount(Message message) {
        message.getMessageProperties().setHeader(VirtualAccountServiceConstant.MQ_HEADER_RETRY_COUNT, 1);
        return message;
    }
}