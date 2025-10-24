package com.commerce.controller.v1;

import com.commerce.controller.dto.request.PageSize;
import com.commerce.controller.dto.response.PageResponse;
import com.commerce.controller.dto.response.ProductDetailResponse;
import com.commerce.controller.dto.response.ProductResponse;
import com.commerce.domain.product.entity.Product;
import com.commerce.domain.product.service.ProductService;
import com.commerce.domain.review.dto.RateSummary;
import com.commerce.domain.review.service.ReviewService;
import com.commerce.support.response.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  private final ProductService productService;
  private final ReviewService reviewService;

  public ProductController(final ProductService productService, final ReviewService reviewService) {
    this.productService = productService;
    this.reviewService = reviewService;
  }

  @GetMapping("/v1/products")
  public ApiResponse<PageResponse<ProductResponse>> findProducts(
      @RequestParam(required = false) List<Long> categoryIds,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "createdAt,desc") String sort
  ) {
    final Page<Product> products = productService.findProductsByCategoryIds(categoryIds, PageSize.of(page, size).toPageable(sort));
    final Page<ProductResponse> productsResponse = products.map(ProductResponse::of);
    return ApiResponse.success(PageResponse.of(productsResponse));
  }

  @GetMapping("/v1/product/{productId}")
  public ApiResponse<ProductDetailResponse> findProduct(
      @PathVariable Long productId
  ) {
    final Product product = productService.findProductById(productId);
    // TODO 쿠폰, 좋아요, QnA 추가
    final RateSummary rateSummary = reviewService.findRateSummary(productId);
    return ApiResponse.success(ProductDetailResponse.of(product, rateSummary));
  }
}
