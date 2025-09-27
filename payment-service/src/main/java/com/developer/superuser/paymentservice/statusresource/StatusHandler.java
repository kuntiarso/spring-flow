package com.developer.superuser.paymentservice.statusresource;

import com.developer.superuser.paymentservice.core.helper.SequenceNumber;
import com.developer.superuser.paymentservice.status.Status;
import com.developer.superuser.paymentservice.status.StatusApiService;
import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.StatusRequest;
import com.developer.superuser.shared.utility.Errors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusHandler {
    private final SequenceNumber sequenceNumber;
    private final StatusApiService statusApiService;
    private final StatusMapper statusMapper;

    public ResponseData<?> checkPaymentStatus(StatusRequest request) {
        try {
            log.debug("Starting to check payment status");
            String requestId = sequenceNumber.generate();
            log.info("Generated sequence number inside check payment status --- {}", request);
            Status status = statusApiService.checkPaymentStatus(statusMapper.mapCore(requestId, request));
            return ResponseData.success(statusMapper.mapResponse(status));
        } catch (Exception ex) {
            log.error("Unknown error occurred while getting payment status", ex);
            return ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
    }
}