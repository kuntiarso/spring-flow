package com.developer.superuser.virtualaccountservice.messaging;

import com.developer.superuser.shared.openapi.contract.VaResponse;

public interface MqPublisherService {
    void sendPaymentStatusQueue(VaResponse response);
}