package com.commerce.domain.review.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.commerce.domain.review.domain.Rating;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RatingTest {

  @Nested
  class 생성_테스트 {

    @Test
    void 별점_생성_테스트() {
      // when & then
      assertThat(Rating.ONE.getValue()).isEqualTo(1);
      assertThat(Rating.TWO.getValue()).isEqualTo(2);
      assertThat(Rating.THREE.getValue()).isEqualTo(3);
      assertThat(Rating.FOUR.getValue()).isEqualTo(4);
      assertThat(Rating.FIVE.getValue()).isEqualTo(5);
    }
  }
}