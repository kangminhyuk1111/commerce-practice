package com.commerce.domain.order.application;

import com.commerce.domain.order.domain.NewOrder;
import com.commerce.domain.order.domain.NewOrderItem;
import com.commerce.domain.order.domain.Order;
import com.commerce.domain.order.domain.OrderItem;
import com.commerce.domain.order.domain.OrderRecord;
import com.commerce.domain.order.domain.OrderStatus;
import com.commerce.domain.order.repository.OrderItemRepository;
import com.commerce.domain.order.repository.OrderRepository;
import com.commerce.domain.product.entity.Product;
import com.commerce.domain.product.repository.ProductRepository;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final ProductRepository productRepository;
  private final OrderKeyGenerator orderKeyGenerator;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  public OrderService(ProductRepository productRepository, OrderKeyGenerator orderKeyGenerator,
      OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
    this.productRepository = productRepository;
    this.orderKeyGenerator = orderKeyGenerator;
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
  }

  /*
   * 주문 생성
   * */
  @Transactional
  public String createOrder(Long userId, NewOrder newOrder) {
    Set<Long> orderProductIds = newOrder.items().stream().map(NewOrderItem::productId)
        .collect(Collectors.toSet());

    Map<Long, Product> productsMap = productRepository.findAllById(orderProductIds)
        .stream()
        .collect(Collectors.toMap(
            Product::getId,
            product -> product
        ));

    validateCreateOrder(newOrder, productsMap, orderProductIds);

    BigDecimal totalPrice = calculateTotalPrice(newOrder, productsMap);

    Order order = new Order(
        orderKeyGenerator.generate(userId), userId, totalPrice, OrderStatus.PENDING
    );

    Order savedOrder = orderRepository.save(order);

    orderItemRepository.saveAll(newOrder.items().stream()
        .map(item -> {
          Product product = productsMap.get(item.productId());
          return new OrderItem(
              product.getId(),
              savedOrder.getId(),
              product.getName(),
              product.getPrice(),
              item.quantity(),
              product.getPrice().multiply(BigDecimal.valueOf(item.quantity()))
          );
        })
        .toList());

    for (NewOrderItem item : newOrder.items()) {
      Product product = productsMap.get(item.productId());
      product.decreaseStock(item.quantity());
    }

    return order.getOrderKey();
  }

  public OrderRecord getOrder(Long userId, String orderKey, OrderStatus orderStatus) {
    if (!orderStatus.equals(OrderStatus.PENDING)) {
      throw new CoreException(ErrorType.ORDER_STATUS_NOT_PENDING);
    }

    Order order = orderRepository.findByOrderKey(orderKey)
        .orElseThrow(() -> new CoreException(ErrorType.ORDER_NOT_FOUND));

    if(!Objects.equals(order.getUserId(), userId)) {
      throw new CoreException(ErrorType.ORDER_USER_INCORRECT);
    }

    List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

    if (items.isEmpty()) {
      throw new CoreException(ErrorType.CART_ITEM_NOT_FOUND);
    }

    return new OrderRecord(
        order.getId(),
        order.getOrderKey(),
        userId,
        order.getTotalPrice(),
        order.getOrderStatus(),
        items
    );
  }

  private BigDecimal calculateTotalPrice(NewOrder newOrder, Map<Long, Product> productsMap) {
    BigDecimal totalPrice = BigDecimal.ZERO;

    for (NewOrderItem item : newOrder.items()) {
      Product product = productsMap.get(item.productId());
      BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.quantity()));
      totalPrice = totalPrice.add(itemTotal);
    }

    return totalPrice;
  }

  private void validateCreateOrder(NewOrder newOrder, Map<Long, Product> productsMap,
      Set<Long> orderProductIds) {
    if (productsMap.isEmpty()) {
      throw new CoreException(ErrorType.CART_IS_EMPTY);
    }

    if (!productsMap.keySet().equals(orderProductIds)) {
      throw new CoreException(ErrorType.PRODUCT_MISMATCH_IN_ORDER);
    }

    for (NewOrderItem item : newOrder.items()) {
      Product product = productsMap.get(item.productId());

      if (product.getStock() < item.quantity()) {
        throw new CoreException(ErrorType.OUT_OF_STOCK);
      }
    }
  }
}
