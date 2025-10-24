package com.commerce.domain.product.repository.impl;

import com.commerce.domain.product.entity.Product;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByIdIn(Collection<Long> ids, Pageable pageable);
}
