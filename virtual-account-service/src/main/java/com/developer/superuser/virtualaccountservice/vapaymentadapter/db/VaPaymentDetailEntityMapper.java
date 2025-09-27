package com.developer.superuser.virtualaccountservice.vapaymentadapter.db;

import com.developer.superuser.shared.embedding.Amount;
import com.developer.superuser.shared.enumeration.Currency;
import com.developer.superuser.virtualaccountservice.core.embedding.Additional;
import com.developer.superuser.virtualaccountservice.core.embedding.AdditionalSetting;
import com.developer.superuser.virtualaccountservice.vapayment.VaDetail;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VaPaymentDetailEntityMapper {
    public VaPaymentDetailEntity toEntity(VaDetail va) {
        AdditionalSetting setting = null;
        if (va.getAdditional().getVirtualAccountConfig() != null) {
            setting = AdditionalSetting.builder()
                    .setReusableStatus(va.getAdditional().getVirtualAccountConfig().getReusableStatus())
                    .setMinAmount(va.getAdditional().getVirtualAccountConfig().getMinAmount())
                    .setMaxAmount(va.getAdditional().getVirtualAccountConfig().getMaxAmount())
                    .build();
        }
        return VaPaymentDetailEntity.builder()
                .setPaymentId(Long.parseLong(va.getPaymentId()))
                .setPartnerId(va.getPartnerId())
                .setInquiryId(va.getInquiryId())
                .setRequestId(va.getRequestId())
                .setCustomerNo(va.getCustomerNo())
                .setVaNo(va.getVaNo())
                .setVaName(va.getVaName())
                .setBilledAmount(Amount.builder()
                        .setValue(va.getBilledAmount().getValue())
                        .setCurrency(Currency.valueOf(va.getBilledAmount().getCurrency()))
                        .build())
                .setAdditional(Additional.builder()
                        .setChannel(va.getAdditional().getChannel())
                        .setHowToPayPage(va.getAdditional().getHowToPayPage())
                        .setHowToPayApi(va.getAdditional().getHowToPayApi())
                        .setVirtualAccountConfig(setting)
                        .build())
                .setTransactionType(va.getTransactionType().name().charAt(0))
                .setExpiredAt(va.getExpiredAt())
                .build();
    }

    public VaPaymentDetailEntity mapUpdate(VaPaymentDetailEntity entity, VaDetail va) {
        if (Objects.nonNull(va.getPaidAmount())) {
            entity.setPaidAmount(Amount.builder()
                    .setValue(va.getPaidAmount().getValue())
                    .setCurrency(Currency.valueOf(va.getPaidAmount().getCurrency()))
                    .build());
        }
        entity.setPaymentStatus(va.getStatus());
        return entity;
    }
}