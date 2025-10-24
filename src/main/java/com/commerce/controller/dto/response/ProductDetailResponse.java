package com.commerce.controller.dto.response;

import com.commerce.domain.product.entity.Product;
import com.commerce.domain.review.dto.RateSummary;
import java.math.BigDecimal;

public record ProductDetailResponse(
    String imagePathUrl,
    String name,
    String description,
    BigDecimal price,
    Double reviewAvg,
    Integer reviewCount
    // TODO 쿠폰, 좋아요, 리뷰, QnA 추가
) {

  public static ProductDetailResponse of(final Product product, final RateSummary rateSummary) {
    return new ProductDetailResponse(
        product.getImagePathUrl(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        rateSummary.getReviewAvg(),
        rateSummary.getReviewCount()
    );
  }
}
