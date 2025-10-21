package com.commerce.domain.product.repository.impl;

import com.commerce.domain.product.entity.ProductCategory;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
  List<ProductCategory> findByCategoryIdIn(Collection<Long> categoryIds);
}
