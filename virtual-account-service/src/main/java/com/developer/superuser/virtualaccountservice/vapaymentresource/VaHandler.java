package com.developer.superuser.virtualaccountservice.vapaymentresource;

import com.developer.superuser.shared.data.ResponseData;
import com.developer.superuser.shared.dto.springflow.VaCreateRequest;
import com.developer.superuser.shared.utility.Errors;
import com.developer.superuser.virtualaccountservice.vapayment.VaApiService;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import com.developer.superuser.virtualaccountservice.vapayment.VaPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaHandler {
    private final VaApiService vaApiService;
    private final VaMapper vaMapper;
    private final VaPersistenceService vaPersistenceService;

    public ResponseData<?> createVa(String requestId, VaCreateRequest request) {
        ResponseData<?> response;
        try {
            log.debug("Start to create va");
            VaDetail va = vaMapper.mapCore(requestId, request);
            va = vaApiService.createVa(va);
            log.info("Successfully create va --- {}", va);
            if (Objects.nonNull(va.getError())) {
                log.error("Received error from create va");
                response = ResponseData.error(va.getError());
            } else {
                vaPersistenceService.create(va);
                log.debug("Successfully created va");
                response = ResponseData.success(vaMapper.mapResponse(va));
            }
        } catch (Exception ex) {
            log.error("Unknown error occurred while creating va ::: ", ex);
            response = ResponseData.error(Errors.internalServerError(ex.getLocalizedMessage()));
        }
        return response;
    }
}