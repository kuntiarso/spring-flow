package com.developer.superuser.virtualaccountservice.core.helper;

import com.developer.superuser.shared.helper.Executor;
import com.developer.superuser.shared.openapi.contract.ErrorData;
import com.developer.superuser.shared.utility.Errors;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import com.developer.superuser.virtualaccountservice.vapaymentresource.VaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class VaApiHelper implements Executor<Supplier<VaDetail>, VaDetail> {
    private final VaMapper vaMapper;

    @Override
    public VaDetail execute(Supplier<VaDetail> supplier) {
        ErrorData error;
        try {
            VaDetail va = supplier.get();
            log.info("Raw va response from doku --- {}", va);
            return vaMapper.mapCore(va);
        } catch (RestClientResponseException rcse) {
            log.error("Receiving unsuccessful response from doku VA api", rcse);
            HttpStatus responseStatus = HttpStatus.resolve(rcse.getStatusCode().value());
            log.error("Response status --- {}", responseStatus);
            error = rcse.getResponseBodyAs(ErrorData.class);
            if (error != null) {
                error.setStatus(rcse.getStatusCode().value());
            } else {
                error = Errors.error(rcse.getStatusCode().value(), rcse.getLocalizedMessage());
            }
        } catch (Exception ex) {
            log.error("Unknown error occurred while calling doku VA api", ex);
            error = Errors.internalServerError(ex.getLocalizedMessage());
        }
        return VaDetail.builder().setError(error).build();
    }
}