package com.developer.superuser.orchestratorservice.integration.filter;

import com.developer.superuser.shared.openapi.contract.ErrorData;
import org.springframework.integration.annotation.Filter;
import org.springframework.stereotype.Component;

@Component
public class ResponseFilter {
    @Filter
    public boolean isSuccess(Object response) {
        return !(response instanceof ErrorData);
    }
}


