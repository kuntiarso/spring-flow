package com.developer.superuser.paymentservice.statusadapter;

import com.developer.superuser.paymentservice.core.property.DokuConfigProperties;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.shared.openapi.contract.DokuStatusRequest;
import com.developer.superuser.shared.openapi.contract.DokuStatusResponse;
import com.developer.superuser.shared.project.springodt.helper.Digest;
import com.developer.superuser.shared.project.springodt.sign.Sign;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class StatusApiMapper {
    private final DokuConfigProperties dokuConfig;
    private final Digest digest;
    private final ObjectMapper mapper;

    public DokuStatusRequest mapRequest(Status status) {
        return DokuStatusRequest.builder()
                .setPartnerServiceId(status.getPartnerId())
                .setCustomerNo(status.getCustomerNo())
                .setVirtualAccountNo(status.getVaNo())
                .build();
    }

    @SneakyThrows
    public Sign mapSign(String token, DokuStatusRequest request) {
        return Sign.builder()
                .setHttpMethod(HttpMethod.POST.name())
                .setEndpoint(dokuConfig.getApi().getEndpoint().get("va-status"))
                .setToken(token)
                .setDigest(digest.generate(mapper.writeValueAsString(request)))
                .setTimestamp(Instant.now())
                .build();
    }

    public Status mapCore(DokuStatusResponse response) {
        return Status.builder()
                .setCode(response.getResponseCode())
                .setMessage(response.getResponseMessage())
                .setFlagReasonEn(response.getVirtualAccountData().getPaymentFlag().getEnglish())
                .setFlagReasonId(response.getVirtualAccountData().getPaymentFlag().getIndonesia())
                .setPartnerId(response.getVirtualAccountData().getPartnerId())
                .setCustomerNo(response.getVirtualAccountData().getCustomerNo())
                .setVaNo(response.getVirtualAccountData().getVaNo())
                .setPaidAmount(response.getVirtualAccountData().getPaidAmount())
                .setBillDetails(response.getVirtualAccountData().getBillDetails())
                .setAcquirerId(response.getAdditionalInfo().getAcquirer().getId())
                .setPaymentId(response.getAdditionalInfo().getPaymentId())
                .build();
    }
}