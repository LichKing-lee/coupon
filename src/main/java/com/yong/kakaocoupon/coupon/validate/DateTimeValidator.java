package com.yong.kakaocoupon.coupon.validate;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.yong.kakaocoupon.coupon.entity.Coupon;

@Component
public class DateTimeValidator implements CouponValidator {
	@Override
	public boolean validate(Coupon coupon, ValidateCondition condition) {
		LocalDateTime dateTime = condition.getDateTime();

		return !(dateTime.isBefore(coupon.getUsableFrom()) || dateTime.isAfter(coupon.getUsableUntil()));
	}
}
