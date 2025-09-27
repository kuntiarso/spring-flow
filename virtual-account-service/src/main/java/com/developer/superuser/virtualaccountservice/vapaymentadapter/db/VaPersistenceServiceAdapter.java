package com.developer.superuser.virtualaccountservice.vapaymentadapter.db;

import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import com.developer.superuser.virtualaccountservice.vapayment.VaPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class VaPersistenceServiceAdapter implements VaPersistenceService {
    private final VaRepository vaRepository;
    private final VaPaymentDetailEntityMapper vaPaymentDetailEntityMapper;

    @Override
    @Transactional
    public void create(VaDetail va) {
        log.debug("Persisting va detail");
        vaRepository.save(vaPaymentDetailEntityMapper.toEntity(va));
    }

    @Override
    @Transactional
    public void update(VaDetail va) {
        log.debug("Updating va detail record");
        VaPaymentDetailEntity entity = vaRepository.getReferenceById(Long.parseLong(va.getPaymentId()));
        vaRepository.save(vaPaymentDetailEntityMapper.mapUpdate(entity, va));
    }
}