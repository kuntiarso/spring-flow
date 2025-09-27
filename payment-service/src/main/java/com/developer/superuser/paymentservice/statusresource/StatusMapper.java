package com.developer.superuser.paymentservice.statusresource;

import com.developer.superuser.paymentservice.core.property.DokuConfigProperties;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.shared.openapi.contract.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusMapper {
    private final DokuConfigProperties dokuConfig;

    public Status mapCore(String requestId, StatusRequest request) {
        return Status.builder()
                .setClientId(dokuConfig.getMerchant().getClientId())
                .setRequestId(requestId)
                .setPartnerId(request.getPartnerId())
                .setCustomerNo(request.getCustomerNo())
                .setVaNo(request.getVaNo())
                .build();
    }

    public Status mapCore(String requestId, VaResponse response) {
        return Status.builder()
                .setClientId(dokuConfig.getMerchant().getClientId())
                .setRequestId(requestId)
                .setPartnerId(response.getPartnerId())
                .setCustomerNo(response.getCustomerNo())
                .setVaNo(response.getVaNo())
                .build();
    }

    public StatusResponse mapResponse(Status status) {
        return StatusResponse.builder()
                .setPartnerId(status.getPartnerId())
                .setCustomerNo(status.getCustomerNo())
                .setVaNo(status.getVaNo())
                .setStatus(status.getFlagReasonEn())
                .setPaidAmount(status.getPaidAmount())
                .setBillDetails(status.getBillDetails())
                .setAdditional(DokuStatusAdditional.builder()
                        .setAcquirer(DokuAcquirer.builder().setId(status.getAcquirerId()).build())
                        .setPaymentId(status.getPaymentId())
                        .build())
                .build();
    }
}