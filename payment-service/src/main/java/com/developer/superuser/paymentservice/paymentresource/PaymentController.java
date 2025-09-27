package com.developer.superuser.paymentservice.paymentresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.PaymentRequest;
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
public class PaymentController {
    private final PaymentHandler paymentHandler;

    @PostMapping("va")
    public ResponseEntity<?> createPaymentVa(@Valid @RequestBody PaymentRequest request) {
        log.info("Request detail for create payment va --- {}", request);
        ResponseData<?> response = paymentHandler.createPaymentVa(request);
        if (response.getBody() instanceof ErrorData error) {
            log.error("Error detail for create payment va --- {}", response);
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        log.info("Response detail for create payment va --- {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}