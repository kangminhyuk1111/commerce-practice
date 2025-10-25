package com.commerce.domain.review.dto;

public class RateSummary {

  private Double reviewAvg;
  private Integer reviewCount;

  public RateSummary(final Double reviewAvg, final Integer reviewCount) {
    this.reviewAvg = reviewAvg;
    this.reviewCount = reviewCount;
  }

  public Double getReviewAvg() {
    return reviewAvg;
  }

  public Integer getReviewCount() {
    return reviewCount;
  }
}
