package com.commerce.controller.dto.request;

import com.commerce.domain.review.dto.ReviewContent;
import com.commerce.domain.review.dto.ReviewTarget;
import com.commerce.domain.review.entity.Rating;
import com.commerce.domain.review.entity.ReviewTargetType;

public record AddReviewRequest(
    Long userId,
    Long targetId,
    ReviewTargetType reviewTargetType,
    String title,
    String description,
    Rating rating
) {
    public ReviewTarget toTarget() {
      return new ReviewTarget(reviewTargetType, targetId);
    }

    public ReviewContent toContent() {
      return new ReviewContent(title, description, rating);
    }
}
