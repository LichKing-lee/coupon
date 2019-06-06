package com.yong.kakaocoupon.coupon;

import static java.util.stream.Collectors.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yong.kakaocoupon.coupon.entity.Coupon;
import com.yong.kakaocoupon.coupon.enumclass.CouponStatus;
import com.yong.kakaocoupon.coupon.validate.ValidateCondition;
import com.yong.kakaocoupon.coupon.validate.ValidateContainer;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CouponService {
	private static final Comparator<Coupon> COUPON_COMPARATOR = Comparator.comparing(
		Coupon::getUsableUntil).thenComparing(Comparator.comparing(Coupon::getDiscountAmount).reversed());

	private CouponRepository couponRepository;
	private ValidateContainer validateContainer;

	public List<Coupon> getUsableSortedCoupons(int amount, LocalDateTime dateTime) {
		return couponRepository.findAllByStatus(CouponStatus.NORMAL).stream()
			.filter(c -> validateContainer.validate(c, new ValidateCondition(amount, dateTime)))
			.sorted(COUPON_COMPARATOR)
			.collect(toList());
	}
}
