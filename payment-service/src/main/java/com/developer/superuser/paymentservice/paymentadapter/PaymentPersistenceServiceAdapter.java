package com.developer.superuser.paymentservice.paymentadapter;

import com.developer.superuser.paymentservice.payment.Payment;
import com.developer.superuser.paymentservice.payment.PaymentPersistenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class PaymentPersistenceServiceAdapter implements PaymentPersistenceService {
    private final PaymentRepository paymentRepository;
    private final PaymentEntityMapper paymentEntityMapper;

    @Override
    @Transactional
    public Payment create(Payment payment) {
        log.debug("Persisting payment detail");
        PaymentEntity savedPayment = paymentRepository.save(paymentEntityMapper.mapInsert(payment));
        log.info("Successfully saved payment detail --- {}", savedPayment);
        return paymentEntityMapper.mapCore(savedPayment);
    }

    @Override
    @Transactional
    public Payment update(Payment payment) {
        log.debug("Updating payment record");
        PaymentEntity paymentEntity = paymentRepository.getReferenceById(Long.parseLong(payment.getId()));
        paymentEntity = paymentRepository.save(paymentEntityMapper.mapUpdate(paymentEntity, payment));
        log.debug("Successfully updated payment record");
        return paymentEntityMapper.mapCore(paymentEntity);
    }
}