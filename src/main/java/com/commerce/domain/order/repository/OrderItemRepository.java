package com.commerce.domain.order.repository;

import com.commerce.domain.order.domain.OrderItem;
import java.util.List;

public interface OrderItemRepository {
  void saveAll(List<OrderItem> orderItems);
  OrderItem save(OrderItem orderItem);
  List<OrderItem> findByOrderId(Long orderId);
}
