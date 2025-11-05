package com.commerce.domain.coupon.repository.impl;

import com.commerce.domain.coupon.domain.OwnedCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOwnedCouponRepository extends JpaRepository<OwnedCoupon, Long> {
  List<OwnedCoupon> findByUserIdAndCouponId(Long userId, Long couponId);
  List<OwnedCoupon> findOwnedCouponByUserId(Long userId);
}
