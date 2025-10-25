package com.commerce.domain.review.application;

import com.commerce.domain.review.domain.ReviewKey;
import com.commerce.domain.review.domain.ReviewTargetType;
import org.springframework.stereotype.Component;

@Component
public class DefaultReviewKeyGenerator implements ReviewKeyGenerator {

  @Override
  public ReviewKey generateReviewKey(final ReviewTargetType reviewTargetType, final Long targetId, final Long userId) {
    return new ReviewKey(targetId, userId, reviewTargetType);
  }
}
