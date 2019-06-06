package com.yong.kakaocoupon.coupon.validate;

import org.springframework.stereotype.Component;

import com.yong.kakaocoupon.coupon.entity.Coupon;
import com.yong.kakaocoupon.coupon.enumclass.CouponStatus;

@Component
public class StatusValidator implements CouponValidator {

	@Override
	public boolean validate(Coupon coupon, ValidateCondition condition) {
		return coupon.getStatus() == CouponStatus.NORMAL;
	}
}
