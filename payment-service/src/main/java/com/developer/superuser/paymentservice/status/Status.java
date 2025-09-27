package com.developer.superuser.paymentservice.status;

import com.developer.superuser.shared.openapi.contract.AmountData;
import com.developer.superuser.shared.openapi.contract.DokuBillDetail;
import com.developer.superuser.shared.openapi.contract.TokenScheme;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true, setterPrefix = "set")
@EqualsAndHashCode(callSuper = false)
@ToString
public class Status {
    private String clientId;
    private String requestId;
    private String token;
    private TokenScheme tokenScheme;
    private String signature;
    private Instant timestamp;

    private String partnerId;
    private String customerNo;
    private String vaNo;
    private String inquiryId;

    private String code;
    private String message;
    private String flagReasonEn;
    private String flagReasonId;
    private AmountData paidAmount;
    private List<DokuBillDetail> billDetails;
    private String acquirerId;
    private String paymentId;
}