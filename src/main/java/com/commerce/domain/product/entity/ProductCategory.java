package com.commerce.domain.product.entity;

import com.commerce.domain.category.entity.Category;
import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_category")
public class ProductCategory extends BaseEntity {

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @ManyToOne(optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  public ProductCategory() {}

  public ProductCategory(final Product product, final Category category) {
    this.product = product;
    this.category = category;
  }

  public Long getProductId() {
    return product.getId();
  }

  public Long getCategoryId() {
    return category.getId();
  }
}
