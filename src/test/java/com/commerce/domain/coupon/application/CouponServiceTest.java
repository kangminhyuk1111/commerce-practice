package com.commerce.domain.coupon.application;

import com.commerce.domain.coupon.repository.CouponRepository;
import com.commerce.domain.coupon.repository.FakeCouponRepository;
import com.commerce.domain.coupon.repository.FakeOwnedCouponRepository;
import com.commerce.domain.coupon.repository.OwnedCouponRepository;
import org.junit.jupiter.api.BeforeEach;

public class CouponServiceTest {

  private CouponService couponService;

  @BeforeEach
  void setUp() {
    CouponRepository couponRepository = new FakeCouponRepository();
    OwnedCouponRepository ownedCouponRepository = new FakeOwnedCouponRepository();
    couponService = new CouponService(couponRepository, ownedCouponRepository);
  }
}
