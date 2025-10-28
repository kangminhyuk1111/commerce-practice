package com.commerce.domain.favorite.domain;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity {

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long productId;

  public Favorite() {
  }

  public Favorite(Long userId, Long productId) {
    this.userId = userId;
    this.productId = productId;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getProductId() {
    return productId;
  }
}
