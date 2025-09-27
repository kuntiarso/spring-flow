package com.developer.superuser.virtualaccountservice.vapayment;

public interface VaPersistenceService {
    void create(VaDetail vaDetail);
    void update(VaDetail vaDetail);
}