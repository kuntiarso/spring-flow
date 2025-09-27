package com.developer.superuser.paymentservice.messaging;

import com.developer.superuser.shared.openapi.contract.StatusResponse;

public interface MqPublisherService {
    void sendPaymentStatusQueueSuccess(StatusResponse response);

    void sendPaymentStatusQueueError(StatusResponse response);
}