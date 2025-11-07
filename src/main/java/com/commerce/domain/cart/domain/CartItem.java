package com.commerce.domain.cart.domain;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long productId;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private LocalDateTime addedAt;

  @Column(nullable = false)
  private LocalDateTime expirationAt;

  @Version
  private Long version;

  public CartItem() {
  }

  public CartItem(Long userId, Long productId, Integer quantity, LocalDateTime addedAt, LocalDateTime expirationAt) {
    this.userId = userId;
    this.productId = productId;
    this.quantity = quantity;
    this.addedAt = addedAt;
    this.expirationAt = expirationAt;
  }

  public void modifyingQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getProductId() {
    return productId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public LocalDateTime getAddedAt() {
    return addedAt;
  }

  public LocalDateTime getExpirationAt() {
    return expirationAt;
  }
}
