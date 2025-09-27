package com.developer.superuser.paymentservice.core.helper;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.helper.Executor;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.utility.Errors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiHelper<A> implements Executor<Supplier<ResponseData<A>>, A> {
    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public A execute(Supplier<ResponseData<A>> supplier) {
        ErrorData error;
        try {
            ResponseData<A> response = supplier.get();
            log.info("Raw response from internal service api --- {}", response);
            if (response != null && response.getBody() != null) {
                log.debug("Returning successful api response");
                return response.getBody();
            }
            error = Errors.internalServerError("Receiving null response body");
        } catch (RestClientResponseException rcse) {
            log.error("Receiving unsuccessful response from internal service api", rcse);
            JsonNode err = mapper.readTree(rcse.getResponseBodyAsString());
            log.error("Error response as json --- {}", err);
            if (err != null) {
                error = Errors.error(rcse.getStatusCode().value(), err.path("code").asText(null), err.path("message").asText(null));
            } else {
                error = Errors.error(rcse.getStatusCode().value(), rcse.getLocalizedMessage());
            }
        } catch (Exception ex) {
            log.error("Unknown error occurred while calling internal service api", ex);
            error = Errors.internalServerError(ex.getLocalizedMessage());
        }
        throw new ResponseStatusException(HttpStatus.valueOf(error.getStatus()), mapper.writeValueAsString(error));
    }
}