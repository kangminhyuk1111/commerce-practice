package com.commerce.domain.product.repository;

import com.commerce.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, Long> {
}
