package com.developer.superuser.tokenservice.tokenresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.TokenGetRequest;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {
    private final TokenHandler tokenHandler;

    @PostMapping
    public ResponseEntity<?> getToken(@Valid @RequestBody TokenGetRequest request) {
        log.info("Request detail for get token --- {}", request);
        ResponseData<?> response = tokenHandler.getToken(request);
        if (response.getBody() instanceof ErrorData error) {
            log.error("Error detail for get token --- {}", response);
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        log.info("Response detail for get token --- {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{clientId}")
    public ResponseEntity<?> evictToken(@PathVariable("clientId") String clientId) {
        ResponseData<?> response = tokenHandler.evictToken(clientId);
        if (response.getBody() instanceof ErrorData error) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}