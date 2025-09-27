package com.developer.superuser.virtualaccountservice.messaging;

import com.developer.superuser.shared.openapi.contract.StatusResponse;

public interface MqListenerService {
    void handlePaymentStatusQueueSuccess(StatusResponse response);

    void handlePaymentStatusQueueError(StatusResponse response);
}