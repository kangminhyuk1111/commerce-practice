package com.commerce.domain.review.domain;

public class ReviewKey {

  private Long targetId;
  private Long userId;
  private ReviewTargetType reviewTargetType;

  public ReviewKey(final Long targetId, final Long userId, final ReviewTargetType reviewTargetType) {
    this.targetId = targetId;
    this.userId = userId;
    this.reviewTargetType = reviewTargetType;
  }

  public String getReviewKey() {
    return String.format("%s-%d-%d", reviewTargetType.name(), targetId, userId);
  }

  public Long getUserId() {
    return userId;
  }
}
