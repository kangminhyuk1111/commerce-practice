package com.commerce.domain.review.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.commerce.domain.review.domain.Rating;
import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.domain.ReviewTargetType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReviewTest {

  @Nested
  class 생성_테스트 {

    @Test
    void 리뷰_생성_테스트() {
      // given & when
      final Review review = new Review(1L, "PRODUCT-1-1", ReviewTargetType.PRODUCT, 1L, "리뷰 타이틀", "리뷰 설명", Rating.FIVE);

      // then
      assertAll(() -> {
        assertThat(review).isNotNull();
        assertThat(review.getTargetId()).isEqualTo(1L);
        assertThat(review.getReviewKey()).isEqualTo("PRODUCT-1-1");
        assertThat(review.getReviewTargetType()).isEqualTo(ReviewTargetType.PRODUCT);
        assertThat(review.getUserId()).isEqualTo(1L);
        assertThat(review.getTitle()).isEqualTo("리뷰 타이틀");
        assertThat(review.getDescription()).isEqualTo("리뷰 설명");
        assertThat(review.getRating()).isEqualTo(Rating.FIVE.getValue());
      });
    }
  }
}