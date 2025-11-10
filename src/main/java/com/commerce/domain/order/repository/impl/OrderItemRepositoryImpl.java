package com.commerce.domain.order.repository.impl;

import com.commerce.domain.order.domain.OrderItem;
import com.commerce.domain.order.repository.OrderItemRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {

  private final JpaOrderItemRepository jpaOrderItemRepository;

  public OrderItemRepositoryImpl(JpaOrderItemRepository jpaOrderItemRepository) {
    this.jpaOrderItemRepository = jpaOrderItemRepository;
  }

  @Override
  public void saveAll(List<OrderItem> orderItems) {
    jpaOrderItemRepository.saveAll(orderItems);
  }

  @Override
  public OrderItem save(OrderItem orderItem) {
    return jpaOrderItemRepository.save(orderItem);
  }

  @Override
  public List<OrderItem> findByOrderId(Long orderId) {
    return jpaOrderItemRepository.findByOrderId(orderId);
  }
}
