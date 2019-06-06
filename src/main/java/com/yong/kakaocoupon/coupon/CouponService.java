package com.yong.kakaocoupon.coupon;

import static java.util.stream.Collectors.*;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yong.kakaocoupon.coupon.entity.Coupon;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CouponService {
	private CouponRepository couponRepository;

	public List<Coupon> getSortedCoupons() {
		return couponRepository.findAll().stream()
			.sorted(Comparator.comparing(Coupon::getUsableUntil).thenComparing(
				Comparator.comparing(Coupon::getDiscountAmount).reversed()))
			.collect(toList());
	}
}
