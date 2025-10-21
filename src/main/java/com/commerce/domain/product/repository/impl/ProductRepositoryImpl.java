package com.commerce.domain.product.repository.impl;

import com.commerce.domain.product.entity.Product;
import com.commerce.domain.product.repository.ProductRepository;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

  private final JpaProductRepository jpaProductRepository;

  public ProductRepositoryImpl(final JpaProductRepository jpaProductRepository) {
    this.jpaProductRepository = jpaProductRepository;
  }

  @Override
  public Page<Product> findAll(final Pageable pageable) {
    return jpaProductRepository.findAll(pageable);
  }

  @Override
  public Page<Product> findByIdIn(final Collection<Long> ids, final Pageable pageable) {
    return jpaProductRepository.findByIdIn(ids, pageable);
  }
}
