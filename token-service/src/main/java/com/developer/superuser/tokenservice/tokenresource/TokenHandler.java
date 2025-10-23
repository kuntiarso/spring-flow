package com.developer.superuser.tokenservice.tokenresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.TokenGetRequest;
import com.developer.superuser.shared.openapi.contract.TokenType;
import com.developer.superuser.shared.utility.Errors;
import com.developer.superuser.tokenservice.core.helper.B2bToken;
import com.developer.superuser.tokenservice.core.property.DokuConfigProperties;
import com.developer.superuser.tokenservice.token.Token;
import com.developer.superuser.tokenservice.token.TokenCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenHandler {
    private final DokuConfigProperties dokuConfig;
    private final TokenCacheService tokenCacheService;
    private final B2bToken b2bToken;
    private final TokenMapper tokenMapper;

    public ResponseData<?> getToken(TokenGetRequest request) {
        String clientId = dokuConfig.merchant().clientId();
        ResponseData<?> response;
        try {
            log.info("Start to get token based on tokenType --- {}", request.tokenType());
            Token token;
            if (TokenType.B2B.equals(request.tokenType())) {
                log.debug("Inside b2b token scope");
                b2bToken.validate(request);
                token = b2bToken.execute(tokenMapper.mapCore(clientId, request));
            } else if (TokenType.B2B2C.equals(request.tokenType())) {
                log.debug("Inside b2b2c token scope");
                token = Token.builder().setError(Errors.error(HttpStatus.NOT_IMPLEMENTED.value())).build();
            } else {
                log.error("Unknown token type");
                token = Token.builder().setError(Errors.badRequest("Unknown token type")).build();
            }
            response = Objects.nonNull(token.getError())
                    ? ResponseData.error(token.getError())
                    : ResponseData.success(tokenMapper.mapResponse(token));
        } catch (Exception ex) {
            log.error("Unknown error occurred while getting token ::: ", ex);
            response = ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
        if (!"200".equalsIgnoreCase(response.getCode())) tokenCacheService.evictTokenB2b(clientId);
        return response;
    }

    public ResponseData<?> evictToken(String clientId) {
        try {
            log.info("Evicting token from cache with clientId --- {}", clientId);
            tokenCacheService.evictTokenB2b(clientId);
            return ResponseData.success();
        } catch (Exception ex) {
            log.error("Unknown error occurred while evicting cached token", ex);
            return ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
    }
}