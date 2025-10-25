package com.commerce.domain.review.support.key;

import com.commerce.domain.review.entity.ReviewTargetType;
import org.springframework.stereotype.Component;

@Component
public class DefaultReviewKeyGenerator implements ReviewKeyGenerator {

  @Override
  public ReviewKey generateReviewKey(final ReviewTargetType reviewTargetType, final Long targetId, final Long userId) {
    return new ReviewKey(targetId, userId, reviewTargetType);
  }
}
