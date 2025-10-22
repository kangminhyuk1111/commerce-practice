package com.commerce.domain.product.entity;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product extends BaseEntity {
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  private String imagePathUrl;

  private String detailedDescription;

  public Product() {
  }

  public Product(final String name, final String description, final BigDecimal price, final String imagePathUrl, final String detailedDescription) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.imagePathUrl = imagePathUrl;
    this.detailedDescription = detailedDescription;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getImagePathUrl() {
    return imagePathUrl;
  }

  public String getDetailedDescription() {
    return detailedDescription;
  }
}
