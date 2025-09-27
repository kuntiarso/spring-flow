package com.developer.superuser.paymentservice.paymentadapter;

import com.developer.superuser.paymentservice.payment.Payment;
import com.developer.superuser.shared.embedding.Amount;
import com.developer.superuser.shared.enumeration.Currency;
import com.developer.superuser.shared.openapi.contract.AmountData;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntityMapper {
    public PaymentEntity mapInsert(Payment payment) {
        return PaymentEntity.builder()
                .setOrderId(Long.parseLong(payment.getOrderId()))
                .setUserId(Long.parseLong(payment.getUserId()))
                .setRequestId(payment.getRequestId())
                .setType(payment.getType())
                .setGateway(payment.getGateway())
                .setAmount(Amount.builder()
                        .setValue(payment.getAmount().getValue())
                        .setCurrency(Currency.valueOf(payment.getAmount().getCurrency()))
                        .build())
                .build();
    }

    public PaymentEntity mapUpdate(PaymentEntity existingEntity, Payment payment) {
        return existingEntity.toBuilder()
                .setStatus(payment.getStatus())
                .setErrorCode(payment.getErrorCode())
                .setErrorMessage(payment.getErrorMessage())
                .setPaidAt(payment.getPaidAt())
                .build();
    }

    public Payment mapCore(PaymentEntity entity) {
        return Payment.builder()
                .setId(String.valueOf(entity.getId()))
                .setOrderId(String.valueOf(entity.getOrderId()))
                .setUserId(String.valueOf(entity.getUserId()))
                .setRequestId(entity.getRequestId())
                .setType(entity.getType())
                .setGateway(entity.getGateway())
                .setAmount(AmountData.builder()
                        .setValue(entity.getAmount().getValue())
                        .setCurrency(entity.getAmount().getCurrency().name())
                        .build())
                .setStatus(entity.getStatus())
                .setErrorCode(entity.getErrorCode())
                .setErrorMessage(entity.getErrorMessage())
                .setPaidAt(entity.getPaidAt())
                .build();
    }
}