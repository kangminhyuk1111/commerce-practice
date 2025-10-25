package com.commerce.domain.review.support.key;

import com.commerce.domain.review.entity.ReviewTargetType;

public interface ReviewKeyGenerator {
  ReviewKey generateReviewKey(ReviewTargetType reviewTargetType, Long targetId, Long userId);
}
