package com.commerce.domain.order.domain;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

  @Column(nullable = false)
  private String orderKey;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private BigDecimal totalPrice;

  @Column(nullable = false)
  private OrderStatus orderStatus;

  private PayType payType;

  private String shippingAddress;

  public Order() {
  }

  public Order(String orderKey, Long userId, BigDecimal totalPrice, OrderStatus orderStatus) {
    this.orderKey = orderKey;
    this.userId = userId;
    this.totalPrice = totalPrice;
    this.orderStatus = orderStatus;
  }

  public void updatePayType(PayType payType) {
    this.payType = payType;
  }

  public void updateShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public String getOrderKey() {
    return orderKey;
  }

  public Long getUserId() {
    return userId;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public PayType getPayType() {
    return payType;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }
}
