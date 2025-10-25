package com.commerce.domain.review.application;

import com.commerce.domain.review.domain.ReviewKey;
import com.commerce.domain.review.domain.ReviewTargetType;

public interface ReviewKeyGenerator {
  ReviewKey generateReviewKey(ReviewTargetType reviewTargetType, Long targetId, Long userId);
}
