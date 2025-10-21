package com.commerce.controller.v1;

import com.commerce.controller.dto.request.PageSize;
import com.commerce.controller.dto.response.PageResponse;
import com.commerce.controller.dto.response.ProductResponse;
import com.commerce.domain.product.entity.Product;
import com.commerce.domain.product.service.ProductService;
import com.commerce.support.response.ApiResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  private final ProductService productService;

  public ProductController(final ProductService productService) {
    this.productService = productService;
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
}
