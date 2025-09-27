package com.developer.superuser.virtualaccountservice.core.embedding;

import com.developer.superuser.shared.openapi.contract.Channel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true, setterPrefix = "set")
@EqualsAndHashCode
@ToString(callSuper = true)
public class Additional {
    @Enumerated(EnumType.STRING)
    @Column(name = "channel", nullable = false, length = 30)
    private Channel channel;

    @Column(name = "how_to_pay_page")
    private String howToPayPage;

    @Column(name = "how_to_pay_api")
    private String howToPayApi;

    @Embedded
    private AdditionalSetting virtualAccountConfig;
}