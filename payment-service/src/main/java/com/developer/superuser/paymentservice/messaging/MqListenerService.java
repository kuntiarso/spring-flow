package com.developer.superuser.paymentservice.messaging;

import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.shared.openapi.contract.VaResponse;
import org.springframework.amqp.core.Message;

public interface MqListenerService {
    void handlePaymentStatusQueue(VaResponse response, Message message);

    void handlePaymentStatusDlq(Status status);
}