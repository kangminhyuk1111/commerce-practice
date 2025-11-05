package com.commerce.domain.coupon.domain;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class Coupon extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CouponType couponType;

  @Column(nullable = false)
  private BigDecimal discount;

  @Column(nullable = false)
  private LocalDateTime expiredAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ApplyTargetType applyTargetType;

  @Column(nullable = true)
  private Long applyTargetId;

  public Coupon() {
  }

  public Coupon(String name, CouponType couponType, BigDecimal discount, LocalDateTime expiredAt, ApplyTargetType applyTargetType, Long applyTargetId) {
    this.name = name;
    this.couponType = couponType;
    this.discount = discount;
    this.expiredAt = expiredAt;
    this.applyTargetType = applyTargetType;
    this.applyTargetId = applyTargetId;
  }

  public String getName() {
    return name;
  }

  public CouponType getCouponType() {
    return couponType;
  }

  public BigDecimal getDiscount() {
    return discount;
  }

  public LocalDateTime getExpiredAt() {
    return expiredAt;
  }

  public ApplyTargetType getApplyTargetType() {
    return applyTargetType;
  }

  public Long getApplyTargetId() {
    return applyTargetId;
  }
}
