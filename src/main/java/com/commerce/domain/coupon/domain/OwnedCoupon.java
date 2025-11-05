package com.commerce.domain.coupon.domain;

import com.commerce.support.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "owned_coupons")
public class OwnedCoupon extends BaseEntity {

  @Column(nullable = false)
  private Long userId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_id", nullable = false)
  private Coupon coupon;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OwnedCouponState state;

  @Column(nullable = false)
  private LocalDateTime issuedAt;

  @Column(nullable = false)
  private LocalDateTime expiredAt;

  @Column(nullable = true)
  private LocalDateTime usedAt;

  public OwnedCoupon() {
  }

  public OwnedCoupon(Long userId, Coupon coupon, OwnedCouponState state, LocalDateTime issuedAt, LocalDateTime expiredAt, LocalDateTime usedAt) {
    this.userId = userId;
    this.coupon = coupon;
    this.state = state;
    this.issuedAt = issuedAt;
    this.expiredAt = expiredAt;
    this.usedAt = usedAt;
  }

  public Long getUserId() {
    return userId;
  }

  public Coupon getCoupon() {
    return coupon;
  }

  public OwnedCouponState getState() {
    return state;
  }

  public LocalDateTime getIssuedAt() {
    return issuedAt;
  }

  public LocalDateTime getExpiredAt() {
    return expiredAt;
  }

  public LocalDateTime getUsedAt() {
    return usedAt;
  }
}