package com.commerce.domain.product.repository;

import com.commerce.domain.product.entity.ProductCategory;
import java.util.Collection;
import java.util.List;

public interface ProductCategoryRepository {
  List<ProductCategory> findByCategoryIdIn(Collection<Long> categoryIds);
  List<ProductCategory> findByProductId(Long productId);
}