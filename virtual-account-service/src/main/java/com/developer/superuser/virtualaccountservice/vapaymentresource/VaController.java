package com.developer.superuser.virtualaccountservice.vapaymentresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.VaRequest;
import com.developer.superuser.shared.openapi.contract.VaResponse;
import com.developer.superuser.virtualaccountservice.messaging.MqPublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("va")
@RequiredArgsConstructor
@Slf4j
public class VaController {
    private final VaHandler vaHandler;
    private final MqPublisherService mqPublisherService;

    @PostMapping("create")
    public ResponseEntity<?> createVa(@Valid @RequestBody VaRequest request) {
        log.info("Request detail for create va --- {}", request);
        ResponseData<?> response = vaHandler.createVa(request);
        if (response.getBody() instanceof ErrorData error) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        if (response.getBody() instanceof VaResponse va) {
            log.debug("Successfully created va, hence triggering payment status check");
            mqPublisherService.sendPaymentStatusQueue(va);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}