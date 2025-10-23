package com.developer.superuser.virtualaccountservice.vapaymentresource;

import com.developer.superuser.shared.dto.springflow.VaCreateRequest;
import com.developer.superuser.shared.dto.springflow.VaCreateResponse;
import com.developer.superuser.shared.openapi.contract.StatusResponse;
import com.developer.superuser.virtualaccountservice.core.enumeration.PaymentStatus;
import com.developer.superuser.virtualaccountservice.core.property.DokuConfigProperties;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VaMapper {
    private final DokuConfigProperties dokuConfig;

    public VaDetail mapCore(String requestId, VaCreateRequest request) {
        return VaDetail.builder()
                .setRequestId(requestId)
                .setClientId(dokuConfig.merchant().clientId())
                .setTokenScheme(request.tokenScheme())
                .setToken(request.token())
                .setPaymentId(request.paymentId())
                .setPartnerId((request.partnerId()))
                .setCustomerNo(request.customerNo())
                .setVaNo(request.vaNo())
                .setVaName(request.vaName())
                .setBilledAmount(request.billedAmount())
                .setTransactionType(request.transactionType())
                .setAdditional(request.additional())
                .build();
    }

    public VaCreateResponse mapResponse(VaDetail va) {
        return VaCreateResponse.builder()
                .withPaymentId(va.getPaymentId())
                .withCustomerNo(va.getCustomerNo())
                .withVaNo(va.getVaNo())
                .withBilledAmount(va.getBilledAmount())
                .withAdditional(va.getAdditional())
                .withExpiredAt(va.getExpiredAt())
                .build();
    }

    public VaDetail mapCore(VaDetail va) {
        VaDetail vaDokuResponse = va.getVirtualAccountData();
        return va.toBuilder()
                .setPaymentId(vaDokuResponse.getPaymentId())
                .setPartnerId(vaDokuResponse.getPartnerId())
                .setCustomerNo(vaDokuResponse.getCustomerNo())
                .setVaNo(vaDokuResponse.getVaNo())
                .setVaName(vaDokuResponse.getVaName())
                .setBilledAmount(vaDokuResponse.getBilledAmount())
                .setTransactionType(vaDokuResponse.getTransactionType())
                .setExpiredAt(vaDokuResponse.getExpiredAt())
                .setAdditional(vaDokuResponse.getAdditional())
                .build();
    }

    public VaDetail mapCoreSuccess(StatusResponse response) {
        return VaDetail.builder()
                .setPaymentId(response.getAdditional().getPaymentId())
                .setPaidAmount(response.getPaidAmount())
                .setStatus(PaymentStatus.PAID)
                .build();
    }

    public VaDetail mapCoreError(StatusResponse response) {
        return VaDetail.builder()
                .setPaymentId(response.getAdditional().getPaymentId())
                .setStatus(PaymentStatus.FAILED)
                .build();
    }
}