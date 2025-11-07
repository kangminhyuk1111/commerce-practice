package com.commerce.domain.cart.repository.impl;

import com.commerce.domain.cart.domain.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartItemRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findByUserId(Long userId);
  Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
}
