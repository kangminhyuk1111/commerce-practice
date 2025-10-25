package com.commerce.domain.review.support.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.commerce.domain.review.dto.ReviewContent;
import com.commerce.domain.review.dto.ReviewTarget;
import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.entity.ReviewTargetType;
import com.commerce.domain.review.repository.FakeReviewRepository;
import com.commerce.domain.review.repository.ReviewRepository;
import com.commerce.domain.review.support.key.ReviewKey;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ReviewManagerTest {

  private ReviewManager reviewManager;
  private ReviewRepository reviewRepository;

  @BeforeEach
  void setUp() {
    reviewRepository = new FakeReviewRepository();
    reviewManager = new ReviewManager(reviewRepository);

    // 테스트 데이터 준비
    final Review review1 = new Review(1L, "PRODUCT-1-1", ReviewTargetType.PRODUCT, 1L,
        "좋아요", "정말 좋은 상품입니다", Rating.FIVE);
    final Review review2 = new Review(1L, "PRODUCT-1-2", ReviewTargetType.PRODUCT, 2L,
        "괜찮아요", "나쁘지 않습니다", Rating.FOUR);
    final Review review3 = new Review(2L, "PRODUCT-2-1", ReviewTargetType.PRODUCT, 1L,
        "별로", "좀 아쉬워요", Rating.TWO);

    reviewRepository.save(review1);
    reviewRepository.save(review2);
    reviewRepository.save(review3);
  }

  @Nested
  class 조회 {

    @Test
    void 리뷰_페이징_조회_성공() {
      // given
      final ReviewTarget reviewTarget = new ReviewTarget(ReviewTargetType.PRODUCT, 1L);
      final Pageable pageable = PageRequest.of(0, 10);

      // when
      final Page<Review> result = reviewManager.findReviews(reviewTarget, pageable);

      // then
      assertAll(() -> {
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("좋아요");
      });
    }

    @Test
    void 상품_ID로_리뷰_조회_성공() {
      // given
      final Long productId = 2L;

      // when
      final List<Review> result = reviewManager.findByProductId(productId);

      // then
      assertAll(() -> {
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("별로");
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
      });
    }
  }

  @Nested
  class 생성 {

    @Test
    void 리뷰_추가_성공() {
      // given
      final ReviewTarget reviewTarget = new ReviewTarget(ReviewTargetType.PRODUCT, 3L);
      final ReviewContent reviewContent = new ReviewContent("좋습니다", "최고의 상품", Rating.FIVE);
      final ReviewKey reviewKey = new ReviewKey(3L, 5L, ReviewTargetType.PRODUCT);

      // when
      final Long reviewId = reviewManager.add(reviewTarget, reviewContent, reviewKey);

      // then
      assertThat(reviewId).isNotNull();

      final Review saved = reviewRepository.findByIdAndUserId(reviewId, 5L).get();
      assertAll(() -> {
        assertThat(saved.getTitle()).isEqualTo("좋습니다");
        assertThat(saved.getDescription()).isEqualTo("최고의 상품");
        assertThat(saved.getRating()).isEqualTo(Rating.FIVE.getValue());
        assertThat(saved.getTargetId()).isEqualTo(3L);
      });
    }
  }

  @Nested
  class 수정 {

    @Test
    void 리뷰_수정_성공() {
      // given
      final ReviewContent newContent = new ReviewContent("정말 좋아요", "최고 품질입니다", Rating.FIVE);

      // when & then
      assertDoesNotThrow(() ->
          reviewManager.update(newContent, 1L, 1L)
      );

      // 수정 확인
      final Review updated = reviewRepository.findByIdAndUserId(1L, 1L).get();
      assertAll(() -> {
        assertThat(updated.getTitle()).isEqualTo("정말 좋아요");
        assertThat(updated.getDescription()).isEqualTo("최고 품질입니다");
        assertThat(updated.getRating()).isEqualTo(Rating.FIVE.getValue());
      });
    }
  }

  @Nested
  class 삭제 {

    @Test
    void 리뷰_삭제_성공() {
      // given
      final Long reviewId = 1L;
      final Long userId = 1L;

      // when & then
      assertDoesNotThrow(() ->
          reviewManager.delete(reviewId, userId)
      );

      // 삭제 확인
      final boolean exists = reviewRepository.findByIdAndUserId(reviewId, userId).isEmpty();
      assertThat(exists).isTrue();
    }
  }
}