package com.developer.superuser.virtualaccountservice.vapayment;

import com.developer.superuser.shared.audit.StandardAuditable;
import com.developer.superuser.shared.openapi.contract.*;
import com.developer.superuser.virtualaccountservice.core.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class VaDetail extends StandardAuditable {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clientId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String requestId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TokenScheme tokenScheme;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String signature;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Instant timestamp;

    @JsonProperty("trxId")
    private String paymentId;
    @JsonProperty("partnerServiceId")
    private String partnerId;
    private String inquiryId;
    private String customerNo;
    @JsonProperty("virtualAccountNo")
    private String vaNo;
    @JsonProperty("virtualAccountName")
    private String vaName;
    @JsonProperty("totalAmount")
    private AmountData billedAmount;
    private AmountData paidAmount;
    @JsonProperty("additionalInfo")
    private AdditionalData additional;
    @JsonProperty("virtualAccountTrxType")
    private TransactionType transactionType;
    @JsonProperty("expiredDate")
    private Instant expiredAt;
    private PaymentStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private VaDetail virtualAccountData;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ErrorData error;
}