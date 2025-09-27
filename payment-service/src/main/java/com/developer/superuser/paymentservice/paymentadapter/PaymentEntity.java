package com.developer.superuser.paymentservice.paymentadapter;

import com.developer.superuser.paymentservice.PaymentServiceConstant;
import com.developer.superuser.paymentservice.core.enumeration.PaymentStatus;
import com.developer.superuser.shared.audit.StandardAuditableEntity;
import com.developer.superuser.shared.embedding.Amount;
import com.developer.superuser.shared.openapi.contract.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Table(name = PaymentServiceConstant.ENTITY_PAYMENT)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true, setterPrefix = "set")
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class PaymentEntity extends StandardAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "request_id", nullable = false, unique = true, length = 14)
    private String requestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private PaymentType type;

    @Column(name = "gateway", nullable = false, length = 10)
    private String gateway;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "amount_value")),
            @AttributeOverride(name = "currency", column = @Column(name = "amount_currency"))
    })
    private Amount amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16)
    private PaymentStatus status;

    @Column(name = "error_code", length = 10)
    private String errorCode;

    @Lob
    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "paid_at")
    private Instant paidAt;
}