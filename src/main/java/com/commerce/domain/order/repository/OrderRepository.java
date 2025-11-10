package com.commerce.domain.order.repository;

import com.commerce.domain.order.domain.Order;
import java.util.Optional;

public interface OrderRepository {
  Order save(Order order);
  Optional<Order> findByOrderKey(String orderKey);
}
