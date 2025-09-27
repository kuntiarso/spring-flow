package com.developer.superuser.paymentservice;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentServiceConstant {
    public final String ENTITY_PAYMENT = "\"payment\"";

    public final String PAYMENT_GATEWAY_DOKU = "DOKU";

    public final String ALGORITHM_SHA256 = "SHA-256";
    public final String ALGORITHM_HMAC_SHA512 = "HmacSHA512";

    public final String HEADER_TIMESTAMP = "X-TIMESTAMP";
    public final String HEADER_SIGNATURE = "X-SIGNATURE";
    public final String HEADER_PARTNER_ID = "X-PARTNER-ID";
    public final String HEADER_EXTERNAL_ID = "X-EXTERNAL-ID";

    public final String MQ_EXCHANGE_PAYMENT_STATUS = "payment.status.exchange";
    public final String MQ_QUEUE_PAYMENT_STATUS = "payment.status.queue";
    public final String MQ_QUEUE_PAYMENT_STATUS_60S = "payment.status.queue.60s";
    public final String MQ_QUEUE_PAYMENT_STATUS_120S = "payment.status.queue.120s";
    public final String MQ_QUEUE_PAYMENT_STATUS_180S = "payment.status.queue.180s";
    public final String MQ_QUEUE_PAYMENT_STATUS_SUCCESS = "payment.status.queue.success";
    public final String MQ_QUEUE_PAYMENT_STATUS_ERROR = "payment.status.queue.error";
    public final String MQ_DLQ_PAYMENT_STATUS = "payment.status.dlq";
    public final String MQ_HEADER_RETRY_COUNT = "x-retry-count";
}