package com.developer.superuser.virtualaccountservice;

import lombok.experimental.UtilityClass;

@UtilityClass
public class VirtualAccountServiceConstant {
    public final String ENTITY_VA_PAYMENT_DETAIL = "\"va_payment_detail\"";

    public final String HEADER_TIMESTAMP = "X-TIMESTAMP";
    public final String HEADER_SIGNATURE = "X-SIGNATURE";
    public final String HEADER_PARTNER_ID = "X-PARTNER-ID";
    public final String HEADER_EXTERNAL_ID = "X-EXTERNAL-ID";
    public final String HEADER_CHANNEL_ID = "CHANNEL-ID";

    public final String ALGORITHM_DIGEST_SHA256 = "SHA-256";
    public final String ALGORITHM_HMAC_SHA512 = "HmacSHA512";

    public final String MQ_EXCHANGE_PAYMENT_STATUS = "payment.status.exchange";
    public final String MQ_QUEUE_PAYMENT_STATUS_60S = "payment.status.queue.60s";
    public final String MQ_QUEUE_PAYMENT_STATUS_SUCCESS = "payment.status.queue.success";
    public final String MQ_QUEUE_PAYMENT_STATUS_ERROR = "payment.status.queue.error";
    public final String MQ_HEADER_RETRY_COUNT = "x-retry-count";
}