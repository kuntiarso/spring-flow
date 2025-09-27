package com.developer.superuser.paymentservice.payment;

import com.developer.superuser.paymentservice.core.enumeration.PaymentStatus;
import com.developer.superuser.shared.audit.StandardAuditable;
import com.developer.superuser.shared.openapi.contract.AmountData;
import com.developer.superuser.shared.openapi.contract.PaymentType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true, setterPrefix = "set")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment extends StandardAuditable {
    private String id;
    private String orderId;
    private String userId;
    private String requestId;
    private PaymentType type;
    private String gateway;
    private AmountData amount;
    private PaymentStatus status;
    private String errorCode;
    private String errorMessage;
    private Instant paidAt;
}