package com.yong.kakaocoupon.coupon.validate;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yong.kakaocoupon.coupon.entity.Coupon;

class MinAmountValidatorTest {
	private MinAmountValidator validator;

	@BeforeEach
	void setUp() {
		validator = new MinAmountValidator();
	}

	@Test
	void 최소금액보다_높으면_성공() {
		var condition = new ValidateCondition(600, null);
		var coupon = mockCoupon(500);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isTrue();
	}

	@Test
	void 최소금액이랑_같으면_성공() {
		var condition = new ValidateCondition(500, null);
		var coupon = mockCoupon(500);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isTrue();
	}

	@Test
	void 최소금액보다_낮으면_실패() {
		var condition = new ValidateCondition(400, null);
		var coupon = mockCoupon(500);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isFalse();
	}

	private Coupon mockCoupon(int minAmount) {
		var coupon = new Coupon();

		coupon.setUseMinAmount(minAmount);

		return coupon;
	}
}