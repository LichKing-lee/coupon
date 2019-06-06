package com.yong.kakaocoupon.coupon.validate;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class ValidateCondition {
	private int amount;
	private LocalDateTime dateTime;
}
