package com.developer.superuser.tokenservice.tokenresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.TokenRequest;
import com.developer.superuser.shared.openapi.contract.TokenType;
import com.developer.superuser.shared.utility.Errors;
import com.developer.superuser.tokenservice.core.helper.B2bTokens;
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
    private final TokenCacheService tokenCacheService;
    private final B2bTokens b2bTokens;
    private final TokenMapper tokenMapper;

    public ResponseData<?> getToken(TokenRequest request) {
        try {
            Token token = null;
            ErrorData error = null;
            log.debug("Getting token based on tokenType");
            if (TokenType.B2B.equals(request.getTokenType())) {
                log.info("Getting b2b token");
                b2bTokens.validate(request);
                token = b2bTokens.execute(tokenMapper.mapCore(request));
            } else if (TokenType.B2B2C.equals(request.getTokenType())) {
                log.info("Getting b2b2c token");
                error = Errors.error(HttpStatus.NOT_IMPLEMENTED.value());
            } else {
                log.error("Unknown token type --- {}", request.getTokenType());
                error = Errors.badRequest("Unknown token type");
            }
            if (error != null) {
                return ResponseData.error(error);
            } else if (Objects.isNull(token)) {
                return ResponseData.error(Errors.internalServerError("Receiving null token"));
            } else if (Objects.nonNull(token.getError())) {
                return ResponseData.error(token.getError());
            }
            return ResponseData.success(tokenMapper.mapResponse(token));
        } catch (Exception ex) {
            log.error("Unknown error occurred while getting token", ex);
            return ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
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