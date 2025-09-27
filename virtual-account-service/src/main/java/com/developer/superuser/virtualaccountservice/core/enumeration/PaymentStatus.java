package com.developer.superuser.virtualaccountservice.core.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    INITIATED("Created but not yet processed", false, false),
    PROCESSING("Payment is in progress", false, false),
    AWAITING_PAYMENT("Waiting for customer action", false, false),
    PAID("Payment completed successfully", true, false),
    SETTLED("Funds settled", true, false),
    FAILED("Payment attempt failed", true, true),
    EXPIRED("Payment expired", true, true),
    CANCELLED("Payment was cancelled", true, true),
    REFUNDED("Payment refunded", true, true),
    CHARGEBACK("Payment disputed and charged back", true, true);

    private final String label;
    private final boolean isFinal;
    private final boolean isError;
}