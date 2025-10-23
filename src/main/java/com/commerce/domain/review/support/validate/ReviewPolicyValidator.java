package com.commerce.domain.review.support.validate;

import com.commerce.domain.order.entity.Order;
import com.commerce.domain.order.repository.OrderRepository;
import com.commerce.domain.review.entity.ReviewTargetType;
import com.commerce.domain.review.repository.ReviewRepository;
import com.commerce.domain.review.support.key.ReviewKey;
import com.commerce.domain.review.support.key.ReviewKeyGenerator;
import com.commerce.support.error.CoreException;
import com.commerce.support.error.ErrorType;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ReviewPolicyValidator {

  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;
  private final ReviewKeyGenerator reviewKeyGenerator;

  public ReviewPolicyValidator(final ReviewRepository reviewRepository, final OrderRepository orderRepository, final ReviewKeyGenerator reviewKeyGenerator) {
    this.reviewRepository = reviewRepository;
    this.orderRepository = orderRepository;
    this.reviewKeyGenerator = reviewKeyGenerator;
  }

  public ReviewKey addNewValidate(Long userId, Long targetId, ReviewTargetType reviewTargetType) {
    final ReviewKey reviewKey = reviewKeyGenerator.generateReviewKey(reviewTargetType, targetId, userId);

    if (reviewTargetType == ReviewTargetType.PRODUCT) {
      // 구매 여부
      // TODO 주문 추가시, 주문이 존재하는지 여부와 userId, targetId 기준으로 일치하는지 검증
      // 중복 리뷰 여부
      validateReviewKey(reviewKey);
      // 리뷰 작성 기간
      validateWriteReviewPeriod(userId, targetId);

      return reviewKey;
    }

    throw new CoreException(ErrorType.INTERNAL_SERVER_ERROR);
  }

  private void validatePurchaseExistence(Long userId, Long targetId) {
    boolean hasPurchase = orderRepository.existsByUserIdAndProductId(userId, targetId);
    if (!hasPurchase) {
      throw new CoreException(ErrorType.ORDER_NOT_PURCHASED);
    }
  }

  private void validateReviewKey(final ReviewKey reviewKey) {
    if (reviewRepository.existByReviewKey(reviewKey.getReviewKey())) {
      throw new CoreException(ErrorType.REVIEW_ALREADY_EXIST);
    }
  }

  private void validateWriteReviewPeriod(final Long userId, final Long targetId) {
    final Order order = orderRepository.findByUserIdAndProductId(userId, targetId)
        .orElseThrow(() -> new CoreException(ErrorType.ORDER_NOT_FOUND));

    if (LocalDateTime.now().isAfter(order.getCreatedAt().plusDays(30))) {
      throw new CoreException(ErrorType.REVIEW_ALREADY_EXIST);
    }
  }
}
