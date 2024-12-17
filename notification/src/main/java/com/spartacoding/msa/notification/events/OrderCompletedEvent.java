package com.spartacoding.msa.notification.events;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCompletedEvent {
  private Long orderId;
  private String productId;
  private BigDecimal totalPrice;
  private String customerEmail;

}
