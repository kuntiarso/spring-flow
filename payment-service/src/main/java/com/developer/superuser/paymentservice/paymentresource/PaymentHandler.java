package com.developer.superuser.paymentservice.paymentresource;

import com.developer.superuser.paymentservice.core.enumeration.PaymentStatus;
import com.developer.superuser.paymentservice.core.helper.SequenceNumber;
import com.developer.superuser.paymentservice.payment.Payment;
import com.developer.superuser.paymentservice.payment.PaymentPersistenceService;
import com.developer.superuser.paymentservice.paymentresource.mapper.PaymentMapper;
import com.developer.superuser.paymentservice.paymentresource.mapper.VaSvcMapper;
import com.developer.superuser.paymentservice.vasvc.VaSvcApiService;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.openapi.contract.PaymentRequest;
import com.developer.superuser.shared.openapi.contract.VaResponse;
import com.developer.superuser.shared.utility.Errors;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentHandler {
    private final ObjectMapper mapper;
    private final SequenceNumber sequenceNumber;
    private final PaymentPersistenceService paymentPersistenceService;
    private final PaymentMapper paymentMapper;
    private final VaSvcApiService vaSvcApiService;
    private final VaSvcMapper vaSvcMapper;

    @SneakyThrows
    public ResponseData<?> createPaymentVa(PaymentRequest request) {
        Payment payment = new Payment();
        ResponseData<?> response;
        ErrorData error;
        try {
            log.debug("Starting to create payment va");
            String requestId = sequenceNumber.generate();
            log.info("Generated sequence number inside create payment va --- {}", requestId);
            payment = paymentPersistenceService.create(paymentMapper.mapCore(requestId, request));
            log.debug("Successfully created payment");
            VaResponse va = vaSvcApiService.createVa(vaSvcMapper.mapRequest(request, payment));
            log.info("Successfully created va --- {}", va);
            response = ResponseData.success(vaSvcMapper.mapResponse(va));
            payment.setStatus(PaymentStatus.AWAITING_PAYMENT);
        } catch (ResponseStatusException rse) {
            log.error("Reasoned error occurred while creating payment va");
            error = mapper.readValue(rse.getReason(), ErrorData.class);
            log.error("reason: {}", rse.getReason());
            response = ResponseData.error(error);
            payment.setStatus(PaymentStatus.FAILED);
            payment.setErrorCode(error.getCode());
            payment.setErrorMessage(error.getMessage());
        } catch (Exception ex) {
            log.error("Unknown error occurred while creating payment va", ex);
            error = Errors.internalServerError(ex.getLocalizedMessage());
            response = ResponseData.error(error);
            payment.setStatus(PaymentStatus.FAILED);
            payment.setErrorCode(error.getCode());
            payment.setErrorMessage(error.getMessage());
        } finally {
            payment = paymentPersistenceService.update(payment);
            log.info("Final payment transaction --- {}", payment);
        }
        return response;
    }
}