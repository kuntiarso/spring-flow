package com.developer.superuser.paymentservice.payment;

public interface PaymentPersistenceService {
    Payment create(Payment payment);
    Payment update(Payment payment);
}