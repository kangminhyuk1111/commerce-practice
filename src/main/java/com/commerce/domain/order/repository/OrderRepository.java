package com.commerce.domain.order.repository;

import com.commerce.domain.order.entity.Order;
import java.util.Optional;

public interface OrderRepository {
  Optional<Order> findByUserIdAndProductId(Long userId, Long productId);
  Boolean existsByUserIdAndProductId(Long userId, Long targetId);
}
