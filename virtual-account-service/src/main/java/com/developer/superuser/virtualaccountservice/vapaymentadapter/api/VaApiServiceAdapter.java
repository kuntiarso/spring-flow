package com.developer.superuser.virtualaccountservice.vapaymentadapter.api;

import com.developer.superuser.shared.project.springodt.sign.Sign;
import com.developer.superuser.shared.project.springodt.sign.Symmetric;
import com.developer.superuser.virtualaccountservice.vapayment.VaApiService;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class VaApiServiceAdapter implements VaApiService {
    private final Symmetric symmetric;
    private final VaApiMapper vaApiMapper;
    private final VaApi vaApi;

    @Override
    public VaDetail createVa(VaDetail va) {
        log.debug("Calling doku api for creating va");
        Sign sign = symmetric.generate(vaApiMapper.mapSign(va));
        log.info("Printing symmetric sign result --- {}", sign);
        va.setSignature(sign.getSignature());
        va.setTimestamp(sign.getTimestamp());
        VaDetail vaResult = vaApi.dokuCreateVa(va);
        if (vaResult.getError() == null) {
            log.debug("Assign channel to va response");
            vaResult.getAdditional().setChannel(va.getAdditional().getChannel());
            vaResult.setRequestId(va.getRequestId());
        }
        return vaResult;
    }
}