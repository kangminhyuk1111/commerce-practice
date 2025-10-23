package com.commerce.domain.order.repository;

import com.commerce.domain.order.entity.Order;
import java.util.Optional;
import org.springframework.stereotype.Repository;

// TODO 추후 구현 필요
@Repository
public class OrderRepositoryImpl implements OrderRepository{

  @Override
  public Optional<Order> findByUserIdAndProductId(final Long userId, final Long productId) {
    return Optional.empty();
  }

  @Override
  public Boolean existsByUserIdAndProductId(final Long userId, final Long targetId) {
    return null;
  }
}
