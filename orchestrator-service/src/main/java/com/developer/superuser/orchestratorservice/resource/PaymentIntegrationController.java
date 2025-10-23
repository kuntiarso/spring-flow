package com.developer.superuser.orchestratorservice.resource;

import com.developer.superuser.orchestratorservice.integration.gateway.PaymentGateway;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/integration/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentIntegrationController {
    private final PaymentGateway paymentGateway;

    @PostMapping("va")
    public ResponseEntity<?> createPaymentVa(@RequestBody PaymentIntegrationDto dto) {
        log.debug("Start integration process for create payment va");
        ResponseData<?> response = paymentGateway.createPaymentVa(dto);
        if (response.getBody() instanceof ErrorData error) {
            log.error("Error integration for create payment va ::: {}", response);
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        log.info("Response integration for create payment va --- {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}