package com.developer.superuser.tokenservice.core.interceptor;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.utility.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RequestErrorInterceptor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> interceptValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        ErrorData error = Errors.error(ex.getStatusCode().value(), errors.toString());
        return new ResponseEntity<>(ResponseData.error(error), HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<?> interceptTypeMismatchOrInvalidJson(Exception ex) {
        ErrorData error = Errors.badRequest(ex.getLocalizedMessage());
        return new ResponseEntity<>(ResponseData.error(error), HttpStatus.valueOf(error.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> interceptGeneralException(Exception ex) {
        ErrorData error = Errors.internalServerError(ex.getLocalizedMessage());
        return new ResponseEntity<>(ResponseData.error(error), HttpStatus.valueOf(error.getStatus()));
    }
}