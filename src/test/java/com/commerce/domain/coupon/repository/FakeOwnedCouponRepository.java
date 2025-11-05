package com.commerce.domain.coupon.repository;

import com.commerce.domain.coupon.domain.OwnedCoupon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeOwnedCouponRepository implements OwnedCouponRepository {

  private final Map<Long, List<OwnedCoupon>> store = new HashMap<>();
  private long idSequence = 1L;

  @Override
  public List<OwnedCoupon> findByUserIdAndCouponId(Long userId, Long couponId) {
    return store.getOrDefault(userId, List.of()).stream()
        .filter(ownedCoupon -> ownedCoupon.getCoupon().getId().equals(couponId))
        .toList();
  }

  @Override
  public OwnedCoupon save(OwnedCoupon ownedCoupon) {
    List<OwnedCoupon> userCoupons = store.computeIfAbsent(ownedCoupon.getUserId(), k -> new ArrayList<>());
    userCoupons.add(ownedCoupon);
    return ownedCoupon;
  }

  @Override
  public List<OwnedCoupon> findOwnedCouponByUserId(Long userId) {
    return new ArrayList<>(store.getOrDefault(userId, List.of()));
  }
}