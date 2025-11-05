package com.commerce.controller.dto.response;

import com.commerce.domain.coupon.domain.CouponType;
import com.commerce.domain.coupon.domain.OwnedCoupon;
import com.commerce.domain.coupon.domain.OwnedCouponState;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OwnedCouponResponse(
    Long id,
    OwnedCouponState state,
    String name,
    CouponType type,
    BigDecimal discount,
    LocalDateTime expiredAt
) {
  public static OwnedCouponResponse of(OwnedCoupon ownedCoupon) {
    return new OwnedCouponResponse(
        ownedCoupon.getId(),
        ownedCoupon.getState(),
        ownedCoupon.getCoupon().getName(),
        ownedCoupon.getCoupon().getCouponType(),
        ownedCoupon.getCoupon().getDiscount(),
        ownedCoupon.getCoupon().getExpiredAt()
    );
  }

  public static List<OwnedCouponResponse> of(List<OwnedCoupon> ownedCoupons) {
    return ownedCoupons.stream()
        .map(OwnedCouponResponse::of)
        .toList();
  }
}