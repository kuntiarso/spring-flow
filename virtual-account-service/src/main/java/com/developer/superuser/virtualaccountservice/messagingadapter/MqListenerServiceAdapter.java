package com.developer.superuser.virtualaccountservice.messagingadapter;

import com.developer.superuser.shared.openapi.contract.StatusResponse;
import com.developer.superuser.virtualaccountservice.VirtualAccountServiceConstant;
import com.developer.superuser.virtualaccountservice.messaging.MqListenerService;
import com.developer.superuser.virtualaccountservice.vapayment.VaPersistenceService;
import com.developer.superuser.virtualaccountservice.vapaymentresource.VaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RequiredArgsConstructor
@Slf4j
public class MqListenerServiceAdapter implements MqListenerService {
    private final VaMapper vaMapper;
    private final VaPersistenceService vaPersistenceService;

    @RabbitListener(queues = VirtualAccountServiceConstant.MQ_QUEUE_PAYMENT_STATUS_SUCCESS)
    @Override
    public void handlePaymentStatusQueueSuccess(StatusResponse response) {
        try {
            log.info("Receiving payment.status.queue.success --- {}", response);
            vaPersistenceService.update(vaMapper.mapCoreSuccess(response));
        } catch (Exception ex) {
            log.error("Unknown error occurred while handling payment.status.queue.success --- {}", ex.getLocalizedMessage());
            throw new AmqpRejectAndDontRequeueException(ex.getLocalizedMessage());
        }
    }

    @RabbitListener(queues = VirtualAccountServiceConstant.MQ_QUEUE_PAYMENT_STATUS_ERROR)
    @Override
    public void handlePaymentStatusQueueError(StatusResponse response) {
        try {
            log.info("Receiving payment.status.queue.error --- {}", response);
            vaPersistenceService.update(vaMapper.mapCoreError(response));
        } catch (Exception ex) {
            log.error("Unknown error occurred while handling payment.status.queue.error --- {}", ex.getLocalizedMessage());
            throw new AmqpRejectAndDontRequeueException(ex.getLocalizedMessage());
        }
    }
}
