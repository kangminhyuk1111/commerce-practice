package com.commerce.domain.coupon.repository.impl;

import com.commerce.domain.coupon.domain.OwnedCoupon;
import com.commerce.domain.coupon.repository.OwnedCouponRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OwnedCouponRepositoryImpl implements OwnedCouponRepository {

  private final JpaOwnedCouponRepository jpaOwnedCouponRepository;

  public OwnedCouponRepositoryImpl(JpaOwnedCouponRepository jpaOwnedCouponRepository) {
    this.jpaOwnedCouponRepository = jpaOwnedCouponRepository;
  }

  public List<OwnedCoupon> findByUserIdAndCouponId(Long userId, Long couponId) {
    return jpaOwnedCouponRepository.findByUserIdAndCouponId(userId, couponId);
  }

  @Override
  public OwnedCoupon save(OwnedCoupon ownedCoupon) {
    return jpaOwnedCouponRepository.save(ownedCoupon);
  }

  @Override
  public List<OwnedCoupon> findOwnedCouponByUserId(Long userId) {
    return jpaOwnedCouponRepository.findOwnedCouponByUserId(userId);
  }
}
