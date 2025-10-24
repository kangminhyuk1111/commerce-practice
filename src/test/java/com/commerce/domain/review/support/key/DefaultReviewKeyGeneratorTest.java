package com.commerce.domain.review.support.key;

import static org.assertj.core.api.Assertions.assertThat;

import com.commerce.domain.review.entity.ReviewTargetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DefaultReviewKeyGeneratorTest {

  private ReviewKeyGenerator reviewKeyGenerator;

  @BeforeEach
  void setUp() {
    reviewKeyGenerator = new DefaultReviewKeyGenerator();
  }

  @Nested
  class 리뷰키_생성 {

    @Test
    void 리뷰키_생성_성공() {
      // given & when
      ReviewKey reviewKey = reviewKeyGenerator.generateReviewKey(ReviewTargetType.PRODUCT, 1L, 1L);

      // then
      assertThat(reviewKey.getReviewKey()).isEqualTo("PRODUCT-1-1");
    }
  }
}