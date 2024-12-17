package com.spartacoding.msa.payment.application;

import com.spartacoding.msa.payment.EventSerializer;
import com.spartacoding.msa.payment.domain.Payment;
import com.spartacoding.msa.payment.domain.PaymentRepository;
import com.spartacoding.msa.payment.events.PaymentCompletedEvent;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

  Logger logger = LoggerFactory.getLogger(PaymentApplicationService.class);
  private final PaymentRepository paymentRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  // TODO PG연동은 스킵. 주문생성에 대한 결제 완료처리, 결제완료에 대한 이벤트 발행
  public void completePayment(Long orderId, BigDecimal totalPrice) {
    Payment payment = new Payment(orderId, totalPrice);
    paymentRepository.save(payment);

    payment.completed();
    paymentRepository.save(payment);

    PaymentCompletedEvent event = new PaymentCompletedEvent(payment.getId(), payment.getOrderId(),
        payment.getStatus());
    kafkaTemplate.send("payment-completed", EventSerializer.serialize(event));


  }

}
