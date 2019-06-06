package com.yong.kakaocoupon.coupon.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.yong.kakaocoupon.coupon.dto.CouponResult;

class CouponTest {
	@Test
	void 쿠폰_할인금액이_상품가격과_동일() {
		var amount = 1000;
		var coupon = newCoupon(1000);

		var actual = coupon.use(amount);

		var expect = CouponResult.builder()
			.amount(amount)
			.paymentAmount(0)
			.discountedAmount(1000)
			.build();

		assertThat(actual).isEqualTo(expect);
	}

	@Test
	void 쿠폰_할인금액이_상품가격보다_큼() {
		var amount = 500;
		var coupon = newCoupon(1000);

		var actual = coupon.use(amount);

		var expect = CouponResult.builder()
			.amount(amount)
			.paymentAmount(0)
			.discountedAmount(500)
			.build();

		assertThat(actual).isEqualTo(expect);
	}

	@Test
	void 쿠폰_할인금액이_상품가격보다_작음() {
		var amount = 5000;
		var coupon = newCoupon(1000);

		var actual = coupon.use(amount);

		var expect = CouponResult.builder()
			.amount(amount)
			.paymentAmount(4000)
			.discountedAmount(1000)
			.build();

		assertThat(actual).isEqualTo(expect);
	}

	private Coupon newCoupon(int discountAmount) {
		var coupon = new Coupon();

		coupon.setDiscountAmount(discountAmount);

		return coupon;
	}
}