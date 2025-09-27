package com.developer.superuser.paymentservice.statusresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.StatusRequest;
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
@RequestMapping("payment")
@RequiredArgsConstructor
@Slf4j
public class StatusController {
    private final StatusHandler statusHandler;

    @PostMapping("status")
    public ResponseEntity<?> checkPaymentStatus(@Valid @RequestBody StatusRequest request) {
        log.info("Request detail for check payment status --- {}", request);
        ResponseData<?> response = statusHandler.checkPaymentStatus(request);
        if (response.getBody() instanceof ErrorData error) {
            log.error("Error detail for check payment status --- {}", response);
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        log.info("Response detail for check payment status --- {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}