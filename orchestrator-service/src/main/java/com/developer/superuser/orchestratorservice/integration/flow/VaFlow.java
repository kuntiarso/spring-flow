package com.developer.superuser.orchestratorservice.integration.flow;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import com.developer.superuser.orchestratorservice.core.property.VaServiceConfigMap;
import com.developer.superuser.orchestratorservice.integration.transformer.VaTransformer;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.VaCreateResponse;
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
public class VaFlow {
    @Bean
    public IntegrationFlow httpCreateVa(VaServiceConfigMap configMap, VaTransformer vaTransformer, DefaultHttpHeaderMapper withDefaultHeaders) {
        log.debug("REST api call for create va");
        URI uri = URI.create(configMap.baseUrl() + configMap.endpoint().get("va-create"));
        log.info("URI for create va --- {}", uri);
        return flow -> flow
                .enrichHeaders(h -> h.headerExpression(OrchestratorServiceConstant.HEADER_X_TEMP, "payload"))
                .transform(vaTransformer, "mapVaCreateRequest")
                .handle(Http.outboundGateway(uri)
                        .httpMethod(HttpMethod.POST)
                        .headerMapper(withDefaultHeaders)
                        .expectedResponseType(new ParameterizedTypeReference<ResponseData<VaCreateResponse>>() {
                        })
                        .extractPayload(true))
                .transform(vaTransformer, "addVaCreateResponse")
                .log(m -> "Response from httpCreateVa --- " + m.getPayload());
    }
}