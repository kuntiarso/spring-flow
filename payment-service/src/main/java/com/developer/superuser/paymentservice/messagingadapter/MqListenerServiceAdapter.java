package com.developer.superuser.paymentservice.messagingadapter;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.developer.superuser.paymentservice.core.helper.SequenceNumber;
import com.developer.superuser.paymentservice.core.utility.Checks;
import com.developer.superuser.paymentservice.messaging.MqListenerService;
import com.developer.superuser.paymentservice.messaging.MqPublisherService;
import com.developer.superuser.paymentservice.payment.PaymentPersistenceService;
import com.developer.superuser.paymentservice.paymentresource.mapper.PaymentMapper;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.paymentservice.status.StatusApiService;
import com.developer.superuser.paymentservice.statusresource.StatusMapper;
import com.developer.superuser.shared.openapi.contract.VaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class MqListenerServiceAdapter implements MqListenerService {
    private final RabbitTemplate rabbitTemplate;
    private final SequenceNumber sequenceNumber;
    private final StatusMapper statusMapper;
    private final StatusApiService statusApiService;
    private final PaymentMapper paymentMapper;
    private final PaymentPersistenceService paymentPersistenceService;
    private final MqPublisherService mqPublisherService;

    @RabbitListener(queues = PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS)
    @Override
    public void handlePaymentStatusQueue(VaResponse response, Message message) {
        int retryCount = (Integer) Optional.ofNullable(message.getMessageProperties().getHeader(PaymentServiceConstant.MQ_HEADER_RETRY_COUNT)).orElse(0);
        log.info("Receiving payment.status.queue by {} attempt(s) --- {}", retryCount, response);
        String requestId = sequenceNumber.generate();
        log.info("Generated sequence number inside payment status queue --- {}", requestId);
        Status status = statusApiService.checkPaymentStatus(statusMapper.mapCore(requestId, response));
        log.info("Printing status result --- {}", status);
        if (Objects.isNull(status) || !Checks.is2xxCode(status.getCode()) || Checks.isFailure(status.getFlagReasonEn())) {
            log.info("Payment status is error, hence sending to dlq");
            rabbitTemplate.convertAndSend(PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS, PaymentServiceConstant.MQ_DLQ_PAYMENT_STATUS, status);
            return;
        } else if (Checks.isSuccess(status.getFlagReasonEn())) {
            log.info("Payment status is success, hence persisting to db");
            paymentPersistenceService.update(paymentMapper.mapCoreSuccess(status));
            mqPublisherService.sendPaymentStatusQueueSuccess(statusMapper.mapResponse(status));
            return;
        }
        retryPaymentStatusQueue(response, status, ++retryCount);
    }

    private void retryPaymentStatusQueue(VaResponse response, Status status, int retryCount) {
        log.info("Payment status remain pending, hence preparing for a retry");
        String nextQueue = switch (retryCount) {
            case 1 -> PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_60S;
            case 2 -> PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_120S;
            case 3 -> PaymentServiceConstant.MQ_QUEUE_PAYMENT_STATUS_180S;
            default -> null;
        };
        if (Objects.nonNull(nextQueue)) {
            log.info("Retrying for the {} attempt(s), hence sending to the {} queue", retryCount, nextQueue);
            rabbitTemplate.convertAndSend(PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS, nextQueue, response, addRetryCount(retryCount));
        } else {
            log.info("Max retries reached, hence sending to dlq");
            rabbitTemplate.convertAndSend(PaymentServiceConstant.MQ_EXCHANGE_PAYMENT_STATUS, PaymentServiceConstant.MQ_DLQ_PAYMENT_STATUS, status);
        }
    }

    private static MessagePostProcessor addRetryCount(int retryCount) {
        return message -> {
            message.getMessageProperties().setHeader(PaymentServiceConstant.MQ_HEADER_RETRY_COUNT, retryCount);
            return message;
        };
    }

    @RabbitListener(queues = PaymentServiceConstant.MQ_DLQ_PAYMENT_STATUS)
    @Override
    public void handlePaymentStatusDlq(Status status) {
        try {
            log.info("Receiving payment.status.dlq --- {}", status);
            paymentPersistenceService.update(paymentMapper.mapCoreError(status));
            mqPublisherService.sendPaymentStatusQueueError(statusMapper.mapResponse(status));
        } catch (Exception ex) {
            log.error("Unknown error occurred while handling payment.status.dql --- {}", ex.getLocalizedMessage());
            throw new AmqpRejectAndDontRequeueException(ex.getLocalizedMessage());
        }
    }
}