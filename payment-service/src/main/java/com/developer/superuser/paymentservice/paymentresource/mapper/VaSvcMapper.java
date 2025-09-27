package com.developer.superuser.paymentservice.paymentresource.mapper;

import com.developer.superuser.paymentservice.core.property.DokuConfigProperties;
import com.developer.superuser.paymentservice.payment.Payment;
import com.developer.superuser.shared.openapi.contract.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VaSvcMapper {
    private final DokuConfigProperties dokuConfig;

    public VaRequest mapRequest(PaymentRequest request, Payment payment) {
        return VaRequest.builder()
                .setClientId(dokuConfig.getMerchant().getClientId())
                .setRequestId(payment.getRequestId())
                .setTokenScheme(TokenScheme.BEARER)
                .setPaymentId(payment.getId())
                .setPartnerId(request.getPartnerId())
                .setCustomerNo(request.getCustomerNo())
                .setVaNo(request.getVaNo())
                .setVaName(request.getVaName())
                .setBilledAmount(payment.getAmount())
                .setTransactionType(request.getTransactionType())
                .setAdditional(AdditionalData.builder().setChannel(request.getChannel()).build())
                .build();
    }

    public PaymentResponse mapResponse(VaResponse response) {
        return PaymentResponse.builder()
                .setPaymentId(response.getPaymentId())
                .setVaNo(response.getVaNo())
                .setAmount(response.getBilledAmount())
                .setAdditionalInfo(response.getAdditional())
                .setExpiredDate(response.getExpiredDate())
                .build();
    }
}