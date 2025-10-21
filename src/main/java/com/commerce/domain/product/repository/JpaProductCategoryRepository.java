package com.commerce.domain.product.repository;

import com.commerce.domain.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductCategoryRepository extends ProductCategoryRepository, JpaRepository<ProductCategory, Long> {
}
