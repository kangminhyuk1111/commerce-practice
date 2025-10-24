package com.commerce.controller.dto.response;

import com.commerce.domain.product.entity.Product;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price, String imagePathUrl, String detailedDescription) {
  public static ProductResponse of(final Product product) {
    return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImagePathUrl(), product.getDetailedDescription());
  }
}
