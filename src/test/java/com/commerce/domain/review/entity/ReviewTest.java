package com.commerce.domain.review.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.commerce.domain.product.entity.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReviewTest {

  @Nested
  class 생성_테스트 {

    @Test
    void 리뷰_생성_테스트() {
      // given
      final Product product = new Product("상품1", "상품1 입니다.", BigDecimal.valueOf(1000000), "http://localhost:33/1", "상품 디테일 설명 페이지");

      // when
      final Review review = new Review(product, "리뷰 타이틀", "리뷰 설명", Rating.FIVE);

      // then
      assertThat(review).isNotNull();
      assertThat(review.getProductId()).isEqualTo(product.getId());
      assertThat(review.getTitle()).isEqualTo("리뷰 타이틀");
      assertThat(review.getDescription()).isEqualTo("리뷰 설명");
      assertThat(review.getRating()).isEqualTo(Rating.FIVE.getValue());
    }
  }
}