package com.commerce.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.commerce.domain.order.repository.OrderRepositoryImpl;
import com.commerce.domain.review.entity.RateSummary;
import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.entity.ReviewTargetType;
import com.commerce.domain.review.repository.FakeReviewRepository;
import com.commerce.domain.review.support.validate.ReviewPolicyValidator;
import com.commerce.domain.review.support.key.DefaultReviewKeyGenerator;
import com.commerce.domain.review.support.key.ReviewKeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReviewServiceTest {

  private ReviewService reviewService;

  @BeforeEach
  void setUp() {
    final FakeReviewRepository fakeReviewRepository = new FakeReviewRepository();
    final ReviewKeyGenerator reviewKeyGenerator = new DefaultReviewKeyGenerator();
    final OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
    final ReviewPolicyValidator reviewPolicyValidator = new ReviewPolicyValidator(fakeReviewRepository, orderRepository,reviewKeyGenerator);
    reviewService = new ReviewService(fakeReviewRepository, reviewPolicyValidator);

    Review review1 = new Review(1L, "PRODUCT-1-1", ReviewTargetType.PRODUCT, 1L, "리뷰 1", "리뷰 설명 1", Rating.FIVE);
    Review review2 = new Review(1L, "PRODUCT-1-2", ReviewTargetType.PRODUCT, 2L, "리뷰 2", "리뷰 설명 2", Rating.FOUR);
    Review review3 = new Review(1L, "PRODUCT-1-3", ReviewTargetType.PRODUCT, 3L, "리뷰 3", "리뷰 설명 3", Rating.THREE);

    fakeReviewRepository.save(review1);
    fakeReviewRepository.save(review2);
    fakeReviewRepository.save(review3);
  }

  @Nested
  class 조회_테스트 {

    @Test
    void 상품의_리뷰에_대한_통계_조회() {
      // given & when
      final RateSummary rateSummary = reviewService.findRateSummary(1L);

      // then
      assertAll(() -> {
        assertThat(rateSummary.getReviewAvg()).isEqualTo(4.0);
        assertThat(rateSummary.getReviewCount()).isEqualTo(3);
      });
    }
  }
}