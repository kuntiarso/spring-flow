package com.developer.superuser.orchestratorservice.integration.flow;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.core.property.TokenServiceConfigMap;
import com.developer.superuser.orchestratorservice.integration.transformer.TokenTransformer;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.TokenGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;

import java.net.URI;

@Configuration
@Slf4j
public class TokenFlow {
    @Bean
    public IntegrationFlow httpGetToken(TokenServiceConfigMap configMap, TokenTransformer tokenTransformer, DefaultHttpHeaderMapper withDefaultHeaders) {
        log.debug("REST api call for get token");
        URI uri = URI.create(configMap.baseUrl() + configMap.endpoint().get("token-get"));
        log.info("URI for get token --- {}", uri);
        return flow -> flow
                .enrichHeaders(h -> h.headerExpression(OrchestratorServiceConstant.HEADER_X_TEMP, "payload"))
                .transform(tokenTransformer, "mapTokenGetRequest")
                .handle(Http.outboundGateway(uri)
                        .httpMethod(HttpMethod.POST)
                        .headerMapper(withDefaultHeaders)
                        .expectedResponseType(new ParameterizedTypeReference<ResponseData<TokenGetResponse>>() {
                        })
                        .extractPayload(true))
                .transform(tokenTransformer, "addTokenGetResponse")
                .log(m -> "Response from httpGetToken --- " + m.getPayload());
    }
}