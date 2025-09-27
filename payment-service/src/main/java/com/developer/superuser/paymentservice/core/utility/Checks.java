package com.developer.superuser.paymentservice.core.utility;

import com.developer.superuser.shared.openapi.contract.StatusType;
import com.google.common.base.Strings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Checks {
    public boolean is2xxCode(String code) {
        return Strings.nullToEmpty(code).startsWith("200");
    }

    public boolean isPending(String status) {
        return StatusType.PENDING.getValue().equalsIgnoreCase(status);
    }

    public boolean isSuccess(String status) {
        return StatusType.SUCCESS.getValue().equalsIgnoreCase(status);
    }

    public boolean isFailure(String status) {
        return !isPending(status) && !isSuccess(status);
    }
}