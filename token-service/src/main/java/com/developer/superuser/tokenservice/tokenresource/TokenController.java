package com.developer.superuser.tokenservice.tokenresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.TokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {
    private final TokenHandler tokenHandler;

    @PostMapping("access")
    public ResponseEntity<?> getToken(@Valid @RequestBody TokenRequest request) {
        log.info("Request detail for get token --- {}", request);
        ResponseData<?> response = tokenHandler.getToken(request);
        if (response.getBody() instanceof ErrorData error) {
            tokenHandler.evictToken(request.getClientId());
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("evict/{clientId}")
    public ResponseEntity<?> evictToken(@PathVariable("clientId") String clientId) {
        ResponseData<?> response = tokenHandler.evictToken(clientId);
        if (response.getBody() instanceof ErrorData error) {
            return new ResponseEntity<>(response, HttpStatus.valueOf(error.getStatus()));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}