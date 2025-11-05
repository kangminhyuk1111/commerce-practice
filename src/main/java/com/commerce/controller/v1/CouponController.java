package com.commerce.controller.v1;

import com.commerce.controller.dto.response.OwnedCouponResponse;
import com.commerce.domain.coupon.application.CouponService;
import com.commerce.domain.coupon.domain.OwnedCoupon;
import com.commerce.support.response.ApiResponse;
import java.util.List;
import org.hibernate.mapping.Any;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

  private final CouponService couponService;

  public CouponController(CouponService couponService) {
    this.couponService = couponService;
  }

  // 쿠폰 다운로드
  @PostMapping("/v1/coupons/{couponId}/download")
  public ApiResponse<Any> downloadCoupon(Long userId, @PathVariable Long couponId) {
    couponService.download(userId, couponId);
    return ApiResponse.success();
  }

  // 소유중인 쿠폰 조회
  @GetMapping("/v1/owned-coupons")
  public ApiResponse<List<OwnedCouponResponse>> getOwnedCoupons(Long userId) {
    List<OwnedCoupon> ownedCoupons = couponService.getOwnedCoupons(userId);
    return ApiResponse.success(OwnedCouponResponse.of(ownedCoupons));
  }
}
