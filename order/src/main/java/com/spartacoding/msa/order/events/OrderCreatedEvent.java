package com.spartacoding.msa.order.events;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

  private Long orderId;
  private String productId;
  private int quantity;
  private BigDecimal totalPrice;


}
