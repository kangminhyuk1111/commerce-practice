package com.commerce.domain.product.repository.impl;

import com.commerce.domain.product.entity.ProductCategory;
import com.commerce.domain.product.repository.ProductCategoryRepository;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

  private final JpaProductCategoryRepository jpaProductCategoryRepository;

  public ProductCategoryRepositoryImpl(final JpaProductCategoryRepository jpaProductCategoryRepository) {
    this.jpaProductCategoryRepository = jpaProductCategoryRepository;
  }

  @Override
  public List<ProductCategory> findByCategoryIdIn(final Collection<Long> categoryIds) {
    return jpaProductCategoryRepository.findByCategoryIdIn(categoryIds);
  }

  @Override
  public List<ProductCategory> findByProductId(Long productId) {
    return jpaProductCategoryRepository.findByProductId(productId);
  }
}
