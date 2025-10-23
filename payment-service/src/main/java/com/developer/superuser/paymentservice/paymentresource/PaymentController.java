package com.developer.superuser.paymentservice.paymentresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.PaymentCreateRequest;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateRequest;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentHandler paymentHandler;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestHeader("X-Request-ID") String requestId, @Valid @RequestBody PaymentCreateRequest request, HttpServletRequest servlet) {
        log.info("Request detail for create payment --- {}, {}", requestId, request);
        ResponseData<?> response = paymentHandler.createPayment(requestId, request);
        if (response.getBody() instanceof ErrorData error) {
            log.error("Error detail for create payment --- {}", response);
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        log.info("Response detail for create payment --- {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updatePayment(@Valid @RequestBody PaymentUpdateRequest request) {
        log.info("Request detail for update payment --- {}", request);
        ResponseData<?> response = paymentHandler.updatePayment(request);
        if (response.getBody() instanceof ErrorData error) {
            log.error("Error detail for update payment --- {}", response);
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        log.info("Response detail for update payment --- {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}