package com.spartacoding.msa.payment.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long orderId;
  private BigDecimal amount;
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  public Payment(Long orderId, BigDecimal amount) {
    this.orderId = orderId;
    this.amount = amount;
    this.status = PaymentStatus.PENDING;
  }

  public void completed() {
    this.status = PaymentStatus.COMPLETED;
  }

  public void fail() {
    this.status = PaymentStatus.FAILED;
  }

}
