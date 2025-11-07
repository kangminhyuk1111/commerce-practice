package com.commerce.domain.cart.repository;

import com.commerce.domain.cart.domain.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
  List<CartItem> findByUserId(Long userId);
  CartItem save(CartItem cartItem);
  void deleteById(Long cartItemId);
  Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
}
