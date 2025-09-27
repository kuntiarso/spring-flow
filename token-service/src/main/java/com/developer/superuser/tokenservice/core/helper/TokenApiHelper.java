package com.developer.superuser.tokenservice.core.helper;

import com.developer.superuser.shared.helper.Executor;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.utility.Errors;
import com.developer.superuser.tokenservice.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.function.Supplier;

@Component
@Slf4j
public class TokenApiHelper implements Executor<Supplier<Token>, Token> {
    @Override
    public Token execute(Supplier<Token> supplier) {
        ErrorData error;
        try {
            return supplier.get();
        } catch (RestClientResponseException rcse) {
            log.error("Receiving unsuccessful response from doku token api", rcse);
            HttpStatus responseStatus = HttpStatus.resolve(rcse.getStatusCode().value());
            log.error("Response status --- {}", responseStatus);
            error = rcse.getResponseBodyAs(ErrorData.class);
            if (error != null) {
                error.setStatus(rcse.getStatusCode().value());
            } else {
                error = Errors.error(rcse.getStatusCode().value());
            }
        } catch (Exception ex) {
            log.error("Unknown error has occurred while calling doku token api", ex);
            error = Errors.internalServerError(ex.getLocalizedMessage());
        }
        return Token.builder().setError(error).build();
    }
}