package com.commerce.domain.order.repository.impl;

import com.commerce.domain.order.domain.Order;
import com.commerce.domain.order.repository.OrderRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

  private final JpaOrderRepository jpaOrderRepository;

  public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository) {
    this.jpaOrderRepository = jpaOrderRepository;
  }

  @Override
  public Order save(Order order) {
    return jpaOrderRepository.save(order);
  }

  @Override
  public Optional<Order> findByOrderKey(String orderKey) {
    return jpaOrderRepository.findByOrderKey(orderKey);
  }
}
