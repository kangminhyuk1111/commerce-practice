package com.commerce.domain.product.service;

import com.commerce.domain.product.entity.Product;
import com.commerce.domain.product.entity.ProductCategory;
import com.commerce.domain.product.repository.ProductCategoryRepository;
import com.commerce.domain.product.repository.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;

  public ProductService(final ProductRepository productRepository, final ProductCategoryRepository productCategoryRepository) {
    this.productRepository = productRepository;
    this.productCategoryRepository = productCategoryRepository;
  }

  /*
  * 2단계 조회를 한 이유
  * JPQL로 join을 사용하지 않아도 구현이 가능했고, 소규모 데이터이기 때문에 쿼리가 지연되지 않는다고 판단함.
  * 만약 성능적인 개선이 필요하다면 JPQL 쿼리를 짜서 조회부분을 변경하고 페이징 처리
  * 오히려 2단계 조회가 코드레벨에서 가독성이 더 높은 것 같다고 느꼈음
  * */
  public Page<Product> findProductsByCategoryIds(List<Long> categoryIds, final Pageable pageable) {
    // 카테고리 아무것도 선택 안하면 모든 상품 조회하도록 구현
    if (categoryIds == null || categoryIds.isEmpty()) {
      return productRepository.findAll(pageable);
    }

    final List<ProductCategory> productCategories = productCategoryRepository.findByCategoryIdIn(categoryIds);

    List<Long> productIds = productCategories.stream()
        .map(pc -> pc.getProduct().getId())
        .distinct()
        .toList();

    if (productIds.isEmpty()) {
      return Page.empty(pageable);
    }

    return productRepository.findByIdIn(productIds, pageable);
  }
}
