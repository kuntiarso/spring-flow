package com.developer.superuser.paymentservice.statusadapter;

import com.developer.superuser.paymentservice.core.utility.Headers;
import com.developer.superuser.paymentservice.paymentresource.mapper.TokenSvcMapper;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.paymentservice.status.StatusApiService;
import com.developer.superuser.paymentservice.tokensvc.TokenSvcApiService;
import com.developer.superuser.shared.openapi.contract.DokuStatusRequest;
import com.developer.superuser.shared.openapi.contract.DokuStatusResponse;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.TokenResponse;
import com.developer.superuser.shared.project.springodt.sign.Sign;
import com.developer.superuser.shared.project.springodt.sign.Symmetric;
import com.developer.superuser.shared.utility.Errors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Slf4j
public class StatusApiServiceAdapter implements StatusApiService {
    private final TokenSvcMapper tokenSvcMapper;
    private final TokenSvcApiService tokenSvcApiService;
    private final Symmetric symmetric;
    private final StatusApiMapper statusApiMapper;
    private final StatusApi statusApi;
    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public Status checkPaymentStatus(Status status) {
        try {
            log.debug("Calling doku get status api");
            TokenResponse token = tokenSvcApiService.getToken(tokenSvcMapper.mapRequest());
            log.info("Printing token b2b result --- {}", token);
            status.setToken(token.getAccessToken());
            status.setTokenScheme(token.getTokenScheme());
            log.debug("Generating signature for get status api");
            DokuStatusRequest request = statusApiMapper.mapRequest(status);
            Sign sign = symmetric.generate(statusApiMapper.mapSign(status.getToken(), request));
            log.info("Printing symmetric sign result --- {}", sign);
            status.setSignature(sign.getSignature());
            status.setTimestamp(sign.getTimestamp());
            DokuStatusResponse response = statusApi.checkPaymentStatus(Headers.multiValueMapHeader(status), request);
            log.info("Printing doku status response --- {}", response);
            return statusApiMapper.mapCore(response);
        } catch (RestClientResponseException rcse) {
            log.error("Receiving unsuccessful response from internal service api", rcse);
            JsonNode err = mapper.readTree(rcse.getResponseBodyAsString());
            log.error("Error response as json --- {}", err);
            ErrorData error;
            if (err != null) {
                error = Errors.error(rcse.getStatusCode().value(), err.path("code").asText(null), err.path("message").asText(null));
            } else {
                error = Errors.error(rcse.getStatusCode().value(), rcse.getLocalizedMessage());
            }
            throw new ResponseStatusException(HttpStatus.valueOf(rcse.getStatusCode().value()), mapper.writeValueAsString(error));
        }
    }
}