package com.yong.kakaocoupon.coupon.validate;

import com.yong.kakaocoupon.coupon.entity.Coupon;

public interface CouponValidator {
	boolean validate(Coupon coupon, ValidateCondition condition);
}
