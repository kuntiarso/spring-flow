package com.developer.superuser.orchestratorservice.integration.enricher;

import com.developer.superuser.orchestratorservice.OrchestratorServiceConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.http.support.DefaultHttpHeaderMapper;

@Configuration
public class PaymentHeaderEnricher {
    @Bean
    public DefaultHttpHeaderMapper withDefaultHeaders() {
        DefaultHttpHeaderMapper mapper = new DefaultHttpHeaderMapper();
        mapper.setOutboundHeaderNames(OrchestratorServiceConstant.HEADER_X_REQUEST_ID, "!" + OrchestratorServiceConstant.HEADER_X_TEMP, "!" + OrchestratorServiceConstant.HEADER_X_URI);
        return mapper;
    }
}