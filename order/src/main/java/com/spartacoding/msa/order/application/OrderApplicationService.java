package com.spartacoding.msa.order.application;

import com.spartacoding.msa.order.EventSerializer;
import com.spartacoding.msa.order.domain.Order;
import com.spartacoding.msa.order.domain.OrderRepository;
import com.spartacoding.msa.order.dto.OrderResponseDto;
import com.spartacoding.msa.order.events.OrderCompletedEvent;
import com.spartacoding.msa.order.events.OrderCreatedEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

  private final OrderRepository orderRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public OrderResponseDto createOrder(String productId, int quantity, String customerEmail,
      BigDecimal totalPrice) {
    // TODO 주문 생성에 필요한 로직과 이벤트를 발행해 주세요
    Order order = new Order(productId, quantity, customerEmail, totalPrice);
    orderRepository.save(order);
    OrderCreatedEvent event = order.createOrderCreatedEvent();

    kafkaTemplate.send("order-create", EventSerializer.serialize(event));
    return convertToDto(order);
  }


  public void completeOrder(Long orderId) {
    // TODO 주문이 성공적으로 완료된 로직과, 이벤트를 발행해 주세요
    Order order = orderRepository.findById(orderId).orElseThrow();
    OrderCompletedEvent event = order.completed();
    orderRepository.save(order);
    kafkaTemplate.send("order-completed", EventSerializer.serialize(event));
  }

  public List<OrderResponseDto> getAllOrders() {
    List<Order> orders = orderRepository.findAll();
    return orders.stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  private OrderResponseDto convertToDto(Order order) {
    return new OrderResponseDto(
        order.getId(),
        order.getProductId(),
        order.getQuantity(),
        order.getTotalPrice(),
        order.getStatus().name(),
        order.getCustomerEmail()
    );
  }


}
