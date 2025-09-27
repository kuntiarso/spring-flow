package com.developer.superuser.tokenservice.core.utility;

import com.developer.superuser.shared.utility.Dates;
import com.developer.superuser.tokenservice.TokenServiceConstant;
import com.developer.superuser.tokenservice.token.Token;
import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@UtilityClass
public class Headers {
    public MultiValueMap<String, String> multiValueMapHeader(Token token) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(TokenServiceConstant.HEADER_CLIENT_KEY, token.getClientId());
        map.add(TokenServiceConstant.HEADER_SIGNATURE, token.getSignature());
        map.add(TokenServiceConstant.HEADER_TIMESTAMP, Dates.toInstantString(token.getTimestamp()));
        return map;
    }
}