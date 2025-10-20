package com.commerce.domain.category.entity;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {

  @Column(nullable = false)
  private String name;

  public Category() {
  }

  public Category(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
