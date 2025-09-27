package com.developer.superuser.orchestratorservice.paymentflowresource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flow/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentFlowController {
    private final MessageChannel paymentChannel;

    @PostMapping("va")
    public ResponseEntity<?> createPaymentVa() {
        paymentChannel.send(MessageBuilder.withPayload("").build());
        return ResponseEntity.ok().build();
    }
}