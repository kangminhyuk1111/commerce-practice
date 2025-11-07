package com.commerce.domain.review.domain;

import com.commerce.domain.review.dto.ReviewContent;
import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Review extends BaseEntity {

  @Column(nullable = false)
  private Long targetId;

  @Column(nullable = false, unique = true)
  private String reviewKey;

  @Enumerated(EnumType.STRING)
  private ReviewTargetType reviewTargetType;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private Rating rating;

  public Review() {
  }

  public Review(final Long targetId, final String reviewKey, final ReviewTargetType reviewTargetType, final Long userId, final String title, final String description, final Rating rating) {
    this.targetId = targetId;
    this.reviewKey = reviewKey;
    this.reviewTargetType = reviewTargetType;
    this.userId = userId;
    this.title = title;
    this.description = description;
    this.rating = rating;
  }

  public void updateContent(final ReviewContent reviewContent) {
    this.title = reviewContent.title();
    this.description = reviewContent.description();
    this.rating = reviewContent.rating();
  }

  public Long getTargetId() {
    return targetId;
  }

  public String getReviewKey() {
    return reviewKey;
  }

  public ReviewTargetType getReviewTargetType() {
    return reviewTargetType;
  }

  public Long getUserId() {
    return userId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Integer getRating() {
    return rating.getValue();
  }

  @Override
  public String toString() {
    return "Review{" +
        "targetId=" + targetId +
        ", reviewKey='" + reviewKey + '\'' +
        ", reviewTargetType=" + reviewTargetType +
        ", userId=" + userId +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", rating=" + rating +
        '}';
  }
}
