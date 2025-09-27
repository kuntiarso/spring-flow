package com.developer.superuser.tokenservice;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenServiceConstant {
    public final String ENTITY_SIGNATURE = "\"signature\"";

    public final String QUERY_FIND_BY_REQUEST_ID = "SELECT * FROM signature s WHERE s.request_id = :requestId";

    public final String KEY_FORMAT_PKCS12 = "PKCS12";

    public final String ALGORITHM_RSA_SHA256 = "SHA256withRSA";

    public final String HEADER_SIGNATURE = "X-SIGNATURE";
    public final String HEADER_TIMESTAMP = "X-TIMESTAMP";
    public final String HEADER_CLIENT_KEY = "X-CLIENT-KEY";

    public final String CACHE_TOKEN_B2B = "b2bTokens";
}