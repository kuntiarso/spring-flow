package com.developer.superuser.virtualaccountservice.core.utility;

import com.developer.superuser.shared.utility.Dates;
import com.developer.superuser.virtualaccountservice.VirtualAccountServiceConstant;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import com.google.common.base.Strings;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@UtilityClass
public class Headers {
    public MultiValueMap<String, String> multiValueMapHeader(VaDetail va) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(VirtualAccountServiceConstant.HEADER_TIMESTAMP, Dates.toInstantString(va.getTimestamp()));
        map.add(VirtualAccountServiceConstant.HEADER_SIGNATURE, va.getSignature());
        map.add(VirtualAccountServiceConstant.HEADER_PARTNER_ID, va.getClientId());
        map.add(VirtualAccountServiceConstant.HEADER_EXTERNAL_ID, va.getRequestId());
        map.add(VirtualAccountServiceConstant.HEADER_CHANNEL_ID, VirtualAccountServiceConstant.HEADER_CHANNEL_ID);
        map.add(HttpHeaders.AUTHORIZATION, Strings.lenientFormat("%s %s", va.getTokenScheme().getValue(), va.getToken()));
        return map;
    }
}