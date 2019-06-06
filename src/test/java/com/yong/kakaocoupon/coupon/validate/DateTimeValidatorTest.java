package com.yong.kakaocoupon.coupon.validate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yong.kakaocoupon.coupon.entity.Coupon;

class DateTimeValidatorTest {
	private DateTimeValidator validator;

	@BeforeEach
	void setUp() {
		validator = new DateTimeValidator();
	}

	@Test
	void 사용일자가_쿠폰기간_이내이면_성공() {
		var condition = new ValidateCondition(500, LocalDateTime.of(2019, 6, 6, 14, 27));
		var coupon = mockCoupon(5, 7);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isTrue();
	}

	@Test
	void 사용일자가_쿠폰시작일자랑_같으면_성공() {
		var condition = new ValidateCondition(500, LocalDateTime.of(2019, 6, 5, 0, 0));
		var coupon = mockCoupon(5, 7);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isTrue();
	}

	@Test
	void 사용일자가_쿠폰종료일자랑_같으면_성공() {
		var condition = new ValidateCondition(500, LocalDateTime.of(2019, 6, 7, 23, 59));
		var coupon = mockCoupon(5, 7);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isTrue();
	}

	@Test
	void 사용일자가_쿠폰시작일자_이전이면_실패() {
		var condition = new ValidateCondition(500, LocalDateTime.of(2019, 6, 4, 23, 59));
		var coupon = mockCoupon(5, 7);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isFalse();
	}

	@Test
	void 사용일자가_쿠폰종료일자_이후면_실패() {
		var condition = new ValidateCondition(500, LocalDateTime.of(2019, 6, 8, 23, 59));
		var coupon = mockCoupon(5, 7);

		var actual = validator.validate(coupon, condition);

		assertThat(actual).isFalse();
	}

	private Coupon mockCoupon(int fromDay, int untilDay) {
		var coupon = new Coupon();

		coupon.setUsableFrom(LocalDateTime.of(2019, 6, fromDay, 0, 0));
		coupon.setUsableUntil(LocalDateTime.of(2019, 6, untilDay, 23, 59));

		return coupon;
	}
}