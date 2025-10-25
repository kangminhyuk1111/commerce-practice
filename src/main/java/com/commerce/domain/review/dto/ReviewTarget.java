package com.commerce.domain.review.dto;

import com.commerce.domain.review.entity.ReviewTargetType;

public record ReviewTarget(ReviewTargetType reviewTargetType, Long targetId) {
  public static ReviewTarget of(ReviewTargetType reviewTargetType, Long targetId) {
    return new ReviewTarget(reviewTargetType, targetId);
  }
}
