package com.developer.superuser.paymentservice.paymentresource;

import com.developer.superuser.paymentservice.payment.Payment;
import com.developer.superuser.paymentservice.payment.PaymentPersistenceService;
import com.developer.superuser.paymentservice.paymentresource.mapper.PaymentMapper;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.PaymentCreateRequest;
import com.developer.superuser.shared.dto.springflow.PaymentUpdateRequest;
import com.developer.superuser.shared.utility.Errors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentHandler {
    private final PaymentPersistenceService paymentPersistenceService;
    private final PaymentMapper paymentMapper;

    public ResponseData<?> createPayment(String requestId, PaymentCreateRequest request) {
        ResponseData<?> response;
        try {
            log.debug("Start to create payment");
            Payment payment = paymentMapper.mapCore(requestId, request);
            payment = paymentPersistenceService.create(payment);
            log.debug("Successfully created payment");
            response = ResponseData.success(paymentMapper.mapPaymentCreateResponse(payment));
        } catch (Exception ex) {
            log.error("Unknown error occurred while creating payment ::: ", ex);
            response = ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
        return response;
    }

    public ResponseData<?> updatePayment(PaymentUpdateRequest request) {
        ResponseData<?> response;
        try {
            log.debug("Start to update payment");
            Payment payment = paymentMapper.mapCore(request);
            payment = paymentPersistenceService.update(payment);
            log.debug("Successfully updated payment");
            response = ResponseData.success(paymentMapper.mapPaymentUpdateResponse(payment));
        } catch (Exception ex) {
            log.error("Unknown error occurred while updating payment ::: ", ex);
            response = ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
        return response;
    }
}