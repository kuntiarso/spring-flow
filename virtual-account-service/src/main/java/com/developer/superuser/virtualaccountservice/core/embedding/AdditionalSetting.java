package com.developer.superuser.virtualaccountservice.core.embedding;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true, setterPrefix = "set")
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdditionalSetting {
    @Column(name = "conf_reusable", columnDefinition = "BIT(1)")
    private boolean reusableStatus;

    @Column(name = "conf_min_amount", precision = 19, scale = 2)
    private BigDecimal minAmount;

    @Column(name = "conf_max_amount", precision = 19, scale = 2)
    private BigDecimal maxAmount;
}