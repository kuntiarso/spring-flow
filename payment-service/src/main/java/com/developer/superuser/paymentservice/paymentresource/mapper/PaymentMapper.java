package com.developer.superuser.paymentservice.paymentresource.mapper;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.developer.superuser.paymentservice.core.enumeration.PaymentStatus;
import com.developer.superuser.paymentservice.core.utility.Checks;
import com.developer.superuser.paymentservice.payment.Payment;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.shared.dto.springflow.PaymentCreateRequest;
import com.developer.superuser.shared.dto.springflow.PaymentCreateResponse;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateRequest;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateResponse;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentMapper {
    public Payment mapCore(String requestId, PaymentCreateRequest request) {
        return Payment.builder()
                .setRequestId(requestId)
                .setOrderId(request.orderId())
                .setUserId(request.userId())
                .setType(request.paymentType())
                .setGateway(PaymentServiceConstant.PAYMENT_GATEWAY_DOKU)
                .setAmount(request.amount())
                .setStatus(PaymentStatus.INITIATED)
                .build();
    }

    public PaymentCreateResponse mapPaymentCreateResponse(Payment payment) {
        return PaymentCreateResponse.builder()
                .withPaymentId(payment.getId())
                .withStatus(payment.getStatus().getLabel())
                .build();
    }

    public Payment mapCore(PaymentUpdateRequest request) {
        ErrorData error = Objects.nonNull(request.error()) ? request.error() : new ErrorData();
        return Payment.builder()
                .setId(request.paymentId())
                .setStatus(PaymentStatus.valueOf(request.status()))
                .setErrorCode(error.getCode())
                .setErrorMessage(error.getMessage())
                .setPaidAt(request.paidAt())
                .build();
    }

    public PaymentUpdateResponse mapPaymentUpdateResponse(Payment payment) {
        return PaymentUpdateResponse.builder()
                .withPaymentId(payment.getId())
                .withStatus(payment.getStatus().getLabel())
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