package com.commerce.domain.review.application;

import com.commerce.domain.review.domain.Review;
import com.commerce.domain.review.domain.ReviewTargetType;
import com.commerce.domain.review.repository.ReviewRepository;
import com.commerce.domain.review.domain.ReviewKey;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ReviewPolicyValidator {

  private final ReviewRepository reviewRepository;
  private final ReviewKeyGenerator reviewKeyGenerator;

  public ReviewPolicyValidator(final ReviewRepository reviewRepository,
      final ReviewKeyGenerator reviewKeyGenerator) {
    this.reviewRepository = reviewRepository;
    this.reviewKeyGenerator = reviewKeyGenerator;
  }

  public ReviewKey addNewValidate(Long userId, Long targetId, ReviewTargetType reviewTargetType) {
    final ReviewKey reviewKey = reviewKeyGenerator.generateReviewKey(reviewTargetType, targetId, userId);

    if (reviewTargetType == ReviewTargetType.PRODUCT) {
      // 구매 여부
      // TODO 주문 추가시, 주문이 존재하는지 여부와 userId, targetId 기준으로 일치 여부 확인
      validatePurchaseExistence(userId, targetId);
      // 중복 리뷰 여부
      validateReviewKey(reviewKey);
      // 리뷰 작성 기간
      // TODO 주문을 조회하여 주문상태가 완료이고 주문생성일이 30일 이내인 경우 리뷰 작성 가능
      validateWriteReviewPeriod(userId, targetId);

      return reviewKey;
    }

    throw new CoreException(ErrorType.REVIEW_TARGET_TYPE_ERROR);
  }

  public void updateValidate(final Long reviewId, final Long userId) {
    final Review review = reviewRepository.findByIdAndUserId(reviewId, userId)
        .orElseThrow(() -> new CoreException(ErrorType.REVIEW_NOT_FOUND));

    if (review.getCreatedAt().plusDays(30).isBefore(LocalDateTime.now())) {
      throw new CoreException(ErrorType.REVIEW_WRITE_DATE_EXPIRED);
    }
  }

  private void validatePurchaseExistence(final Long userId, final Long targetId) {

  }

  private void validateReviewKey(final ReviewKey reviewKey) {
    if (reviewRepository.existByReviewKey(reviewKey.getReviewKey())) {
      throw new CoreException(ErrorType.REVIEW_ALREADY_EXIST);
    }
  }

  private void validateWriteReviewPeriod(final Long userId, final Long targetId) {

  }
}
