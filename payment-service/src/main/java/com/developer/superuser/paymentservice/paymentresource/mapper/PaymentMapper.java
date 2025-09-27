package com.developer.superuser.paymentservice.paymentresource.mapper;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.developer.superuser.paymentservice.core.enumeration.PaymentStatus;
import com.developer.superuser.paymentservice.core.utility.Checks;
import com.developer.superuser.paymentservice.payment.Payment;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.shared.openapi.contract.PaymentRequest;
import com.developer.superuser.shared.openapi.contract.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentMapper {
    public Payment mapCore(String requestId, PaymentRequest request) {
        return Payment.builder()
                .setOrderId(request.getOrderId())
                .setUserId(request.getUserId())
                .setRequestId(requestId)
                .setType(request.getPaymentType())
                .setGateway(PaymentServiceConstant.PAYMENT_GATEWAY_DOKU)
                .setAmount(request.getBilledAmount())
                .build();
    }

    public Payment mapCoreSuccess(Status status) {
        return Payment.builder()
                .setId(status.getPaymentId())
                .setStatus(PaymentStatus.PAID)
                .setPaidAt(Instant.now())
                .build();
    }

    public Payment mapCoreError(Status status) {
        String code = null, message = null;
        if (Objects.nonNull(status) && !Checks.is2xxCode(status.getCode())) {
            code = status.getCode();
            message = status.getMessage();
        } else if (Objects.nonNull(status)) {
            code = status.getCode();
            message = status.getFlagReasonEn();
        }
        return Payment.builder()
                .setId(status.getPaymentId())
                .setStatus(PaymentStatus.FAILED)
                .setErrorCode(code)
                .setErrorMessage(message)
                .build();
    }
}