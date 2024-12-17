package com.spartacoding.msa.notification.infrastructure.messaging;

import com.spartacoding.msa.notification.EventSerializer;
import com.spartacoding.msa.notification.events.OrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

  private final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

  // TODO 주문완료에 대한 이벤트를 구독, 처리. 주문 완료에 대한 알림 메세지는 로그로 출력.
  @KafkaListener(topics = "order-completed", groupId = "notification-msa-group")
  public void handleOrderCompletedEvent(String message) {
    OrderCompletedEvent event = EventSerializer.deserialize(message, OrderCompletedEvent.class);
    logger.info("\n[notification] 주문완료 (" + event.getOrderId() + ")\n 총 금액:" + event.getTotalPrice() + "\n고객이메일:" + event.getCustomerEmail());

  }

}
