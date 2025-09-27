package com.developer.superuser.paymentservice.core.utility;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.shared.utility.Dates;
import com.google.common.base.Strings;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@UtilityClass
public class Headers {
    public MultiValueMap<String, String> multiValueMapHeader(Status status) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(PaymentServiceConstant.HEADER_TIMESTAMP, Dates.toInstantString(status.getTimestamp()));
        map.add(PaymentServiceConstant.HEADER_SIGNATURE, status.getSignature());
        map.add(PaymentServiceConstant.HEADER_PARTNER_ID, status.getClientId());
        map.add(PaymentServiceConstant.HEADER_EXTERNAL_ID, status.getRequestId());
        map.add(HttpHeaders.AUTHORIZATION, Strings.lenientFormat("%s %s", status.getTokenScheme(), status.getToken()));
        return map;
    }
}