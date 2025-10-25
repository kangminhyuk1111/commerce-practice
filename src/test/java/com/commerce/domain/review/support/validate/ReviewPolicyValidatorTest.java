package com.commerce.domain.review.support.validate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.Review;
import com.commerce.domain.review.entity.ReviewTargetType;
import com.commerce.domain.review.repository.FakeReviewRepository;
import com.commerce.domain.review.repository.ReviewRepository;
import com.commerce.domain.review.support.key.DefaultReviewKeyGenerator;
import com.commerce.domain.review.support.key.ReviewKey;
import com.commerce.domain.review.support.key.ReviewKeyGenerator;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import com.commerce.utils.BaseReflectionUtils;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReviewPolicyValidatorTest {

  private ReviewPolicyValidator reviewPolicyValidator;
  private ReviewRepository reviewRepository;

  @BeforeEach
  void setUp() {
    final ReviewKeyGenerator reviewKeyGenerator = new DefaultReviewKeyGenerator();
    reviewRepository = new FakeReviewRepository();
    reviewPolicyValidator = new ReviewPolicyValidator(reviewRepository, reviewKeyGenerator);

    final Review review = new Review(999L,
        reviewKeyGenerator.generateReviewKey(ReviewTargetType.PRODUCT, 999L, 999L).getReviewKey(),
        ReviewTargetType.PRODUCT, 999L, "테스트", "테스트 입니다.", Rating.FIVE);

    reviewRepository.save(review);
  }

  @Nested
  class 리뷰_정책_검증 {

    @Nested
    class 신규_생성 {

      @Test
      void 상품에_대한_리뷰_검증_성공() {
        // given
        final Long userId = 1L;
        final Long targetId = 1L;
        final ReviewTargetType reviewTargetType = ReviewTargetType.PRODUCT;

        // when
        final ReviewKey reviewKey = reviewPolicyValidator.addNewValidate(userId, targetId,
            reviewTargetType);

        // then
        assertAll(() -> {
          assertThat(reviewKey.getReviewKey()).isEqualTo("PRODUCT-1-1");
          assertThat(reviewKey.getUserId()).isEqualTo(1L);
        });
      }

      // INVALID 타입인 경우는 Controller 테스트에서 처리 해야됨
      @Test
      void 타겟이_null_타입인_경우_예외_발생() {
        // given
        final Long userId = 1L;
        final Long targetId = 1L;
        final ReviewTargetType reviewTargetType = null;

        // when & then
        assertThatThrownBy(
            () -> reviewPolicyValidator.addNewValidate(userId, targetId, reviewTargetType))
            .isInstanceOf(CoreException.class)
            .hasMessage(ErrorType.REVIEW_TARGET_TYPE_ERROR.getMessage());
      }

      @Test
      void 이미_존재하는_리뷰_키인_경우_예외_처리() {
        // given
        final Long userId = 999L;
        final Long targetId = 999L;
        final ReviewTargetType reviewTargetType = ReviewTargetType.PRODUCT;

        // when & then
        assertThatThrownBy(
            () -> reviewPolicyValidator.addNewValidate(userId, targetId, reviewTargetType))
            .isInstanceOf(CoreException.class)
            .hasMessage(ErrorType.REVIEW_ALREADY_EXIST.getMessage());
      }
    }

    @Nested
    class 업데이트 {

      @Test
      void 업데이트에_대한_리뷰_검증_성공() {
        // given
        final Long userId = 999L;
        final Long reviewId = 1L;

        // when & then
        assertDoesNotThrow(() ->
            reviewPolicyValidator.updateValidate(reviewId, userId)
        );
      }

      @Test
      void 업데이트_요청시_리뷰가_존재하지_않는_경우_예외_처리() {
        // given
        final Long userId = 999L;
        final Long reviewId = 999L;

        // when & then
        assertThatThrownBy(() -> reviewPolicyValidator.updateValidate(reviewId, userId))
            .isInstanceOf(CoreException.class)
            .hasMessage(ErrorType.REVIEW_NOT_FOUND.getMessage());
      }

      @Test
      void 업데이트_요청시_유저가_존재하지_않는_경우_예외_처리() {
        // given
        final Long userId = 1L;
        final Long reviewId = 1L;

        // when & then
        assertThatThrownBy(() -> reviewPolicyValidator.updateValidate(reviewId, userId))
            .isInstanceOf(CoreException.class)
            .hasMessage(ErrorType.REVIEW_NOT_FOUND.getMessage());
      }

      @Nested
      class 작성_가능_일자_정책_검증 {

        private Review found;

        @BeforeEach
        void setUp() {
          found = reviewRepository.findByIdAndUserId(1L, 999L).get();
          BaseReflectionUtils.setCreatedAt(found, LocalDateTime.now().minusDays(31));
        }

        @Test
        void 업데이트_요청시_생성_일자가_정책일자를_초과하면_예외_처리() {
          // given
          final Long userId = 999L;
          final Long reviewId = 1L;

          // when & then
          assertThatThrownBy(() -> reviewPolicyValidator.updateValidate(reviewId, userId))
              .isInstanceOf(CoreException.class)
              .hasMessage(ErrorType.REVIEW_WRITE_DATE_EXPIRED.getMessage());
        }
      }
    }
  }
}