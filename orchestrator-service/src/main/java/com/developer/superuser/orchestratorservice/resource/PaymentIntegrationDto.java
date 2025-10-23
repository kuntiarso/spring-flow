package com.developer.superuser.orchestratorservice.resource;

import com.developer.superuser.shared.dto.springflow.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntegrationDto {
    private PaymentCreateRequest paymentCreateRequest;
    private PaymentCreateResponse paymentCreateResponse;
    private TokenGetRequest tokenGetRequest;
    private TokenGetResponse tokenGetResponse;
    private VaCreateRequest vaCreateRequest;
    private VaCreateResponse vaCreateResponse;
    private PaymentUpdateRequest paymentUpdateRequest;
    private PaymentUpdateResponse paymentUpdateResponse;
}