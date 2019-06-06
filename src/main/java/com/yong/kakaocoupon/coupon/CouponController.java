package com.yong.kakaocoupon.coupon;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yong.kakaocoupon.coupon.entity.Coupon;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CouponController {
	private CouponService couponService;

	@GetMapping("/coupons")
	public List<Coupon> test(
		@RequestParam int amount,
		@RequestParam(required = false) LocalDateTime dateTime) {
		return couponService.getUsableSortedCoupons();
	}
}
