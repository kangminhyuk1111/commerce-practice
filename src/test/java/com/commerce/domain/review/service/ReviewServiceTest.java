package com.commerce.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.commerce.domain.review.dto.ReviewContent;
import com.commerce.domain.review.dto.ReviewTarget;
import com.commerce.domain.review.dto.RateSummary;
import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.entity.ReviewTargetType;
import com.commerce.domain.review.repository.FakeReviewRepository;
import com.commerce.domain.review.support.persistence.ReviewManager;
import com.commerce.domain.review.support.validate.ReviewPolicyValidator;
import com.commerce.domain.review.support.key.DefaultReviewKeyGenerator;
import com.commerce.domain.review.support.key.ReviewKeyGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ReviewServiceTest {

  private ReviewService reviewService;
  private FakeReviewRepository fakeReviewRepository;

  @BeforeEach
  void setUp() {
    fakeReviewRepository = new FakeReviewRepository();
    final ReviewKeyGenerator reviewKeyGenerator = new DefaultReviewKeyGenerator();
    final ReviewPolicyValidator reviewPolicyValidator = new ReviewPolicyValidator(fakeReviewRepository, reviewKeyGenerator);
    final ReviewManager reviewManager = new ReviewManager(fakeReviewRepository);
    reviewService = new ReviewService(reviewManager, reviewPolicyValidator);

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

    @Test
    void 상품의_리뷰_페이징_조회_성공() {
      // given
      final ReviewTarget reviewTarget = new ReviewTarget( ReviewTargetType.PRODUCT, 1L);
      final Pageable pageable = PageRequest.of(0, 10);

      // when
      final Page<Review> result = reviewService.findReviews(reviewTarget, pageable);

      // then
      assertAll(() -> {
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent()).hasSize(3);
      });
    }
  }

  @Nested
  class 생성_테스트 {

    @Test
    void 리뷰_추가_성공() {
      // given
      final ReviewTarget reviewTarget = new ReviewTarget(ReviewTargetType.PRODUCT, 2L);
      final ReviewContent reviewContent = new ReviewContent("새로운 리뷰", "새로운 리뷰 설명", Rating.FIVE);
      final Long userId = 10L;

      // when
      reviewService.addReview(reviewTarget, reviewContent, userId);

      // then - 리뷰가 추가되었는지 확인
      final RateSummary rateSummary = reviewService.findRateSummary(2L);
      assertAll(() -> {
        assertThat(rateSummary.getReviewCount()).isEqualTo(1);
        assertThat(rateSummary.getReviewAvg()).isEqualTo(5.0);
      });
    }
  }

  @Nested
  class 수정_테스트 {

    @Test
    void 리뷰_수정_성공() {
      // given
      final Long reviewId = 1L;
      final Long userId = 1L;
      final ReviewContent newContent = new ReviewContent("수정된 리뷰", "수정된 설명", Rating.ONE);

      // when
      reviewService.updateReview(reviewId, userId, newContent);

      // then - 수정된 리뷰 확인
      final Review updated = fakeReviewRepository.findByIdAndUserId(reviewId, userId).get();

      assertAll(() -> {
        assertThat(updated.getTitle()).isEqualTo("수정된 리뷰");
        assertThat(updated.getDescription()).isEqualTo("수정된 설명");
        assertThat(updated.getRating()).isEqualTo(Rating.ONE.getValue());
      });
    }
  }

  @Nested
  class 삭제_테스트 {

    @Test
    void 리뷰_삭제_성공() {
      // given
      final Long reviewId = 1L;
      final Long userId = 1L;

      // when
      reviewService.deleteReview(reviewId, userId);

      // then - 리뷰가 삭제되었는지 확인
      final RateSummary rateSummary = reviewService.findRateSummary(1L);
      assertThat(rateSummary.getReviewCount()).isEqualTo(2);
    }
  }
}