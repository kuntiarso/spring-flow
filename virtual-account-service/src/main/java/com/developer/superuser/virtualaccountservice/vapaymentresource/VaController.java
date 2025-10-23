package com.developer.superuser.virtualaccountservice.vapaymentresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.VaCreateRequest;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/va")
@RequiredArgsConstructor
@Slf4j
public class VaController {
    private final VaHandler vaHandler;

    @PostMapping
    public ResponseEntity<?> createVa(@RequestHeader("X-Request-ID") String requestId, @Valid @RequestBody VaCreateRequest request) {
        log.info("Request detail for create payment --- {}, {}", requestId, request);
        ResponseData<?> response = vaHandler.createVa(requestId, request);
        if (response.getBody() instanceof ErrorData error) {
            log.error("Error detail for create va ::: {}", response);
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        log.info("Response detail for create va --- {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}