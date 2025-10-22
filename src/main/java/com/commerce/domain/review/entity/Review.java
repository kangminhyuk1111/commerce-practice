package com.commerce.domain.review.entity;

import com.commerce.domain.product.entity.Product;
import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Review extends BaseEntity {

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Rating rating;

  public Review() {
  }

  public Review(final Product product, final String title, final String description, final Rating rating) {
    this.product = product;
    this.title = title;
    this.description = description;
    this.rating = rating;
  }

  public Long getProductId() {
    return product.getId();
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Integer getRating() {
    return rating.getValue();
  }
}
