package com.commerce.domain.review.support.key;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.commerce.domain.review.domain.ReviewKey;
import com.commerce.domain.review.domain.ReviewTargetType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReviewKeyTest {

  @Nested
  class 리뷰키_생성 {

    @Test
    void 리뷰키_필드_검증() {
      // given & when
      ReviewKey reviewKey = new ReviewKey(1L, 1L, ReviewTargetType.PRODUCT);

      // then
      assertAll(() -> {
        assertThat(reviewKey.getUserId()).isEqualTo(1L);
        assertThat(reviewKey.getReviewKey()).isEqualTo("PRODUCT-1-1");
      });
    }
  }
}