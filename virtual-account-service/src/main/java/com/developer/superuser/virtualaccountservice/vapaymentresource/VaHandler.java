package com.developer.superuser.virtualaccountservice.vapaymentresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.openapi.contract.VaRequest;
import com.developer.superuser.shared.utility.Errors;
import com.developer.superuser.virtualaccountservice.vapayment.VaApiService;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import com.developer.superuser.virtualaccountservice.vapayment.VaPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaHandler {
    private final VaApiService vaApiService;
    private final VaMapper vaMapper;
    private final VaPersistenceService vaPersistenceService;

    public ResponseData<?> createVa(VaRequest request) {
        try {
            log.debug("Starting to create va");
            VaDetail va = vaApiService.createVa(vaMapper.mapCore(request));
            log.info("Printing create va result --- {}", va);
            if (va.getError() != null) {
                log.error("Receiving error from create va");
                return ResponseData.error(va.getError());
            }
            vaPersistenceService.create(va);
            log.debug("Successfully created va");
            return ResponseData.success(vaMapper.mapResponse(va));
        } catch (Exception ex) {
            log.error("Unknown error occurred while creating va", ex);
            return ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
    }
}