package com.commerce.domain.review.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RateSummaryTest {

  @Nested
  class 생성_테스트 {

    @Test
    void 리뷰_통계_생성_테스트() {
      // given
      Double avg = 0.0;
      Integer count = Integer.MIN_VALUE;

      // when
      final RateSummary rateSummary = new RateSummary(avg, count);

      // then
      assertThat(rateSummary.getReviewAvg()).isEqualTo(avg);
      assertThat(rateSummary.getReviewCount()).isEqualTo(count);
    }
  }
}