package com.yong.kakaocoupon.coupon.validate;

import org.springframework.stereotype.Component;

import com.yong.kakaocoupon.coupon.entity.Coupon;

@Component
public class MinAmountValidator implements CouponValidator {
	@Override
	public boolean validate(Coupon coupon, ValidateCondition condition) {
		int amount = condition.getAmount();

		return coupon.getUseMinAmount() <= amount;
	}
}
