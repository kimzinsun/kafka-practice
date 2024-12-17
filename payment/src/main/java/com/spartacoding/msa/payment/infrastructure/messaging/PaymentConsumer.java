package com.spartacoding.msa.payment.infrastructure.messaging;

import com.spartacoding.msa.payment.EventSerializer;
import com.spartacoding.msa.payment.application.PaymentApplicationService;
import com.spartacoding.msa.payment.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentApplicationService paymentApplicationService;
    private final Logger logger = LoggerFactory.getLogger(PaymentConsumer.class);

    // TODO 주문 생성에 대한 이벤트 구독, 처리
    @Transactional
    @KafkaListener(topics = "order-create", groupId = "payment-msa-group")
    public void handleOrderCreatedEvent(String message) {
        OrderCreatedEvent event = EventSerializer.deserialize(message, OrderCreatedEvent.class);
        paymentApplicationService.completePayment(event.getOrderId(), event.getTotalPrice());

    }
}
