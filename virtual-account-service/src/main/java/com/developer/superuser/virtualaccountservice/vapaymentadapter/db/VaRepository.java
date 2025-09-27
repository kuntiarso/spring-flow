package com.developer.superuser.virtualaccountservice.vapaymentadapter.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VaRepository extends JpaRepository<VaPaymentDetailEntity, Long> {
}