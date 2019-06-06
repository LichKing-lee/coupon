package com.yong.kakaocoupon.coupon.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

// 모든 필드의 타입이 같으므로 헷갈릴 여지가 있어 builder 사용
@Builder
@Data
public class CouponResult {
	private int amount;
	private int paymentAmount;
	private int discountedAmount;
}
