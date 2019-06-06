package com.yong.kakaocoupon.coupon.validate;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.yong.kakaocoupon.coupon.entity.Coupon;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ValidateContainer implements CouponValidator {
	private Set<CouponValidator> validators;

	@Override
	public boolean validate(Coupon coupon, ValidateCondition condition) {
		return validators.stream()
			.allMatch(v -> v.validate(coupon, condition));
	}
}
