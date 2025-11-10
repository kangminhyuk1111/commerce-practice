package com.commerce.controller.v1;

import com.commerce.domain.order.domain.NewOrder;

public record CreateOrderRequest(
    Long productId,
    Integer quantity
) {
  public NewOrder toNewOrder(Long userId) {
    return new NewOrder(userId, productId, quantity);
  }
}
