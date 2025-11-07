package com.commerce.domain.cart.repository.impl;

import com.commerce.domain.cart.domain.CartItem;
import com.commerce.domain.cart.repository.CartItemRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemRepositoryImpl implements CartItemRepository {

  private final JpaCartItemRepository jpaCartItemRepository;

  public CartItemRepositoryImpl(JpaCartItemRepository jpaCartItemRepository) {
    this.jpaCartItemRepository = jpaCartItemRepository;
  }

  @Override
  public List<CartItem> findByUserId(Long userId) {
    return jpaCartItemRepository.findByUserId(userId);
  }

  @Override
  public CartItem save(CartItem cartItem) {
    return jpaCartItemRepository.save(cartItem);
  }

  @Override
  public void deleteById(Long cartItemId) {
    jpaCartItemRepository.deleteById(cartItemId);
  }

  @Override
  public Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId) {
    return jpaCartItemRepository.findByUserIdAndProductId(userId, productId);
  }
}
