package com.developer.superuser.paymentservice.vasvc;

import com.developer.superuser.shared.openapi.contract.VaRequest;
import com.developer.superuser.shared.openapi.contract.VaResponse;

public interface VaSvcApiService {
    VaResponse createVa(VaRequest request);
}