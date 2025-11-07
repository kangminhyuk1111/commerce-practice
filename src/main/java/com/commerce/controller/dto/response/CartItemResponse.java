package com.commerce.controller.dto.response;

import com.commerce.domain.product.entity.Product;

public record CartItemResponse(
    Long cartItemId,
    Product product,
    Integer quantity
) {
  public static CartItemResponse of(Long id, Product product, Integer quantity) {
    return new CartItemResponse(
        id, product, quantity
    );
  }
}
